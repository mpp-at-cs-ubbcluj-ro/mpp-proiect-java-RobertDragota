package org.mpp2024.RcpProtocol;


import org.mpp2024.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;


public class AppServicesRpcProxy implements ServiceAppInterface {
    private String host;
    private int port;

    private AppObserverInterface client;

    private ObjectInputStream input;
    private ObjectOutputStream output;
    private Socket connection;

    private BlockingQueue<Response> qresponses;
    private volatile boolean finished;

    public AppServicesRpcProxy(String host, int port) {
        this.host = host;
        this.port = port;
        qresponses = new LinkedBlockingQueue<Response>();
    }


    private void closeConnection() {
        finished = true;
        try {
            input.close();
            output.close();
            connection.close();
            client = null;
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void sendRequest(Request request) throws AppException {
        try {
            output.writeObject(request);
            output.flush();
        } catch (IOException e) {
            throw new AppException("Error sending object " + e);
        }

    }

    private Response readResponse() throws AppException {
        Response response = null;
        try {

            response = qresponses.take();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return response;
    }

    private void initializeConnection() throws AppException {
        try {
            connection = new Socket(host, port);
            output = new ObjectOutputStream(connection.getOutputStream());
            output.flush();
            input = new ObjectInputStream(connection.getInputStream());
            finished = false;
            startReader();
        } catch (IOException e) {
           e.printStackTrace();
        }
    }

    private void startReader() {
        Thread tw = new Thread(new ReaderThread());
        tw.start();
    }


    private void handleUpdate(Response response) {
        if (response.type() == ResponseType.UPDATE) {

            Iterable<Trip> list = (Iterable<Trip>) response.data();
            System.out.println("Trips list " + list);
            try {
                client.updateTrips(list);
            } catch (AppException e) {
                e.printStackTrace();
            }
        }


    }

    private boolean isUpdate(Response response) {
        return response.type() == ResponseType.UPDATE;
    }

    @Override
    public void Login(Account account, AppObserverInterface observer) throws AppException {
        initializeConnection();
        Request request = new Request.Builder().type(RequestType.LOGIN).data(account).build();
        sendRequest(request);
        Response response = readResponse();
        if (response.type() == ResponseType.OK) {
            this.client = observer;
            return;
        }
        if (response.type() == ResponseType.ERROR) {
            closeConnection();
            throw new AppException("Login error");
        }
    }

    @Override
    public void Register(String usernanme, String password) throws AppException {
        Request request = new Request.Builder().type(RequestType.REGISTER).data(new Account(usernanme, password)).build();
        sendRequest(request);
        Response response = readResponse();
        if (response.type() == ResponseType.OK) {

            return;
        }
        if (response.type() == ResponseType.ERROR) {
            closeConnection();
            throw new AppException("Register error");
        }
    }

    @Override
    public void MakeReservation(Account account, String name, String phone, Long tickets, org.mpp2024.Trip trip) throws AppException {

        Request request = new Request.Builder().type(RequestType.MAKE_RESERVATION).data(new Reservation(account, name, phone, tickets, trip)).build();
        sendRequest(request);
        Response response = readResponse();
        if (response.type() == ResponseType.OK) {

            return;
        }
        if (response.type() == ResponseType.ERROR) {
            throw new AppException("Make reservation error");
        }

    }


    @Override
    public Iterable<Trip> Get_All_Trips() {
        Request request = new Request.Builder().type(RequestType.GET_ALL_TRIP).build();
        try {
            sendRequest(request);
            Response response = readResponse();
            if (response.type() == ResponseType.GET_ALL_TRIP) {
                System.out.println("Accounts list " + response.data());
                return (Iterable<Trip>) response.data();
            }
        } catch (AppException e) {
            e.printStackTrace();

        }
        return null;
    }


    @Override
    public Iterable<Trip> Get_All_Trip_By_Destination_From_To(String destination,LocalDateTime date, LocalDateTime startDate, LocalDateTime finishDate) throws AppException {
        Trip trip = new Trip(destination,".", 1L,1L,date,startDate, finishDate);
        Request request = new Request.Builder().type(RequestType.GET_ALL_TRIP_BY_DESTINATION_FROM_TO).data(trip).build();
        try {
            sendRequest(request);
            Response response = readResponse();
            if (response.type() == ResponseType.GET_ALL_TRIP_BY_DESTINATION_FROM_TO) {
                System.out.println("Accounts list " + response.data());
                return (Iterable<Trip>) response.data();
            }
        } catch (AppException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public Account MakeAccount(String username, String password) throws AppException {
        Request request = new Request.Builder().type(RequestType.MAKE_ACCOUNT).data(new Account(username, password)).build();
        sendRequest(request);
        Response response = readResponse();
        if (response.type() == ResponseType.OK) {
            return (Account) response.data();
        }
        if (response.type() == ResponseType.ERROR) {
            throw new AppException("Make account error");
        }
        return null;
    }


    @Override
    public void Logout(Account account, AppObserverInterface observer) throws AppException {
        Request request = new Request.Builder().type(RequestType.LOGOUT).data(account).build();
        sendRequest(request);
        Response response = readResponse();
        closeConnection();
        if (response.type() == ResponseType.ERROR) {
            throw new AppException("Logout error");
        }

    }

    private class ReaderThread implements Runnable {
        public void run() {
            while (!finished) {
                try {
                    Object response = input.readObject();
                    System.out.println("response received " + response);
                    if (isUpdate((Response) response)) {
                        handleUpdate((Response) response);
                    } else {

                        try {
                            qresponses.put((Response) response);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (IOException e) {
                    System.out.println("Reading error " + e);
                } catch (ClassNotFoundException e) {
                    System.out.println("Reading error " + e);
                }
            }
        }
    }
}
