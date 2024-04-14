package org.mpp2024.RcpProtocol;


import org.mpp2024.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;


public class AppUserRpcReflectionWorker implements Runnable, AppObserverInterface {
    private ServiceAppInterface server;
    private Socket connection;

    private ObjectInputStream input;
    private ObjectOutputStream output;
    private volatile boolean connected;

    public AppUserRpcReflectionWorker(ServiceAppInterface server, Socket connection) {
        this.server = server;
        this.connection = connection;
        try {
            output = new ObjectOutputStream(connection.getOutputStream());
            output.flush();
            input = new ObjectInputStream(connection.getInputStream());
            connected = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        while (connected) {
            try {
                Object request = input.readObject();
                Response response = handleRequest((Request) request);
                if (response != null) {
                    sendResponse(response);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        try {
            input.close();
            output.close();
            connection.close();
        } catch (IOException e) {
            System.out.println("Error " + e);
        }
    }


    private static Response okResponse = new Response.Builder().type(ResponseType.OK).build();

    private Response handleRequest(Request request) {
        Response response = null;
        String handlerName = "handle" + (request).type();
        System.out.println("HandlerName " + handlerName);
        try {
            Method method = this.getClass().getDeclaredMethod(handlerName, Request.class);
            response = (Response) method.invoke(this, request);
            System.out.println("Method " + handlerName + " invoked");
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return response;
    }

    private Response handleLOGIN(Request request) {
        System.out.println("Login request ..." + request.type());
        Account account = (Account) request.data();
        try {
            server.Login(account, this);
            return okResponse;
        } catch (AppException e) {
            connected = false;
            return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
        }
    }

    private Response handleLOGOUT(Request request) {
        System.out.println("Logout request...");
        Account account = (Account) request.data();
        try {
            server.Logout(account, this);
            connected = false;
            return okResponse;

        } catch (AppException e) {
            return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
        }
    }



    private Response handleREGISTER(Request request) {
        System.out.println("Register request...");
        Account account = (Account) request.data();
        try {
            server.Register(account.getName(), account.getPassword());
            return okResponse;

        } catch (AppException e) {
            return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
        }
    }



    private Response handleMAKE_RESERVATION(Request request) {
        System.out.println("Make Reservation request...");
        Reservation reservation = (Reservation) request.data();
        Account account = reservation.getAccount();
        try {
            server.MakeReservation(account, account.getName(), reservation.getPhoneNumber(), reservation.getTickets(), reservation.getTrip());
            return okResponse;

        } catch (AppException e) {
            return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
        }
    }

    private Response handleGET_ALL_TRIP_BY_DESTINATION_FROM_TO(Request request) throws AppException {
        System.out.println("Get All request...");
        Trip destination = (Trip) request.data();
        try {
            Iterable<Trip> list = server.Get_All_Trip_By_Destination_From_To(destination.getDestination(), destination.getStartHour().getHour(), destination.getFinishHour().getHour());
            return new Response.Builder().type(ResponseType.GET_ALL_TRIP_BY_DESTINATION_FROM_TO).data(list).build();
        } catch (AppException e) {
            return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
        }
    }

    private Response handleGET_ALL_TRIP(Request request) throws AppException {
        System.out.println("Get All request...");
        try{
            var list = server.Get_All_Trips();
            return new Response.Builder().type(ResponseType.GET_ALL_TRIP).data(list).build();
        } catch (AppException e) {
            return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
        }
    }

    private void sendResponse(Response response) throws IOException {
        System.out.println("sending response " + response);
        synchronized (output) {
            output.writeObject(response);
            output.flush();
        }
    }

    @Override
    public void updateTrips(Iterable<Trip> list) throws AppException {
        Response resp=new Response.Builder().type(ResponseType.UPDATE).data(list).build();
        try {
            sendResponse(resp);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
