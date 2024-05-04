package org.mpp2024.app_server;

import org.mpp2024.*;
import org.mpp2024.Utility.Validation.ValidException;

import javax.security.auth.callback.LanguageCallback;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServiceDispatcher implements ServiceAppInterface {
    private final ServiceAccountInferace accountService;
    private final ServiceReservationInterface reservationService;
    private final ServiceTripInterface tripService;
    private Map<Long, AppObserverInterface> loggedUsers;
    private final int defaultThreadsNo = 5;

    public ServiceDispatcher(ServiceAccountInferace accountService, ServiceReservationInterface reservationService, ServiceTripInterface tripService) {
        this.accountService = accountService;
        this.reservationService = reservationService;
        this.tripService = tripService;
        loggedUsers = new ConcurrentHashMap<>();
        ;
    }


    private void notifyClients(Iterable<Trip> trips) throws AppException {
        System.out.println("Notify");

        ExecutorService executor = Executors.newFixedThreadPool(defaultThreadsNo);
        for (Long username : loggedUsers.keySet()) {
            AppObserverInterface client = loggedUsers.get(username);
            if (client != null)
                executor.execute(() -> {
                    try {
                        System.out.println("Notifying [" + username + "]");

                        try {
                            client.updateTrips(trips);
                        } catch (AppException e) {
                            System.err.println("Error notifying " + e);
                        }

                    } catch (Exception e) {
                        System.err.println("Error notifying " + e);
                    }
                });
        }

        executor.shutdown();
    }


    @Override
    public synchronized Iterable<Trip> Get_All_Trips() throws AppException {
        return tripService.getAll();
    }


    @Override
    public synchronized Iterable<Trip> Get_All_Trip_By_Destination_From_To(String destination, LocalDateTime date, LocalDateTime startDate, LocalDateTime finishDate) throws AppException {
        return tripService.filterTrips(destination,date, startDate, finishDate);
    }


    @Override
    public synchronized Account MakeAccount(String username, String password) throws ValidException {
        var account = accountService.createAccount(username, password);
        accountService.add(account);
        return account;
    }


    @Override
    public synchronized void Login(Account account, AppObserverInterface observer) throws AppException {
        Optional<Account> account1 = accountService.findByUsername(account.getName());
        if (account1.isPresent() && account1.get().getPassword().equals(account.getPassword())) {
            if (loggedUsers.get(account1.get().getId()) != null)
                throw new AppException("User already logged in.");
            loggedUsers.put(account1.get().getId(), observer);
            account.setId(account1.get().getId());

        } else
            throw new AppException("Authentication failed.");

    }

    @Override
    public synchronized void Register(String username, String password) throws AppException {
        Account account = new Account(username, password);
        Optional<Account> accountExists = accountService.find(account.getId());

        if (accountExists.isPresent()) {
            throw new AppException("Account already exists.");
        } else {
            accountService.add(account);
        }
    }

    @Override
    public synchronized void Logout(Account account, AppObserverInterface observer) throws AppException {
        Optional<Account> accountExists = accountService.find(account.getId());

        if (accountExists.isPresent()) {
            if (loggedUsers.get(account.getId()) == null) {
                throw new AppException("User is not logged in.");
            }
            loggedUsers.remove(account.getId());
        } else {
            throw new AppException("Account does not exist.");
        }
    }

    @Override
    public synchronized void MakeReservation(Account account, String name, String phone, Long tickets, Trip trip) throws AppException {
        Reservation reservation = reservationService.createReservation(account, name, phone, tickets, trip);
        reservationService.add(reservation);
        trip.setAvailableSeats(trip.getAvailableSeats() - reservation.getTickets());
        tripService.update(trip).orElseThrow(() -> new AppException("Trip not found."));
        notifyClients(tripService.getAll());
    }
}
