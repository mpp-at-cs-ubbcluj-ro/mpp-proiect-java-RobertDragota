package org.mpp2024.app_server;


import org.mpp2024.*;
import org.mpp2024.Utility.Validation.Validator_Reservation;

import java.util.Optional;

public class Service_Reservation implements ServiceReservationInterface {

    private final Repo_Reservation repo_reservation;
    private final Validator_Reservation validator_reservation;

    public Service_Reservation(Repo_Reservation repo_reservation, Validator_Reservation validator_reservation) {
        this.repo_reservation = repo_reservation;
        this.validator_reservation = validator_reservation;
    }

    /**
     * @param reservation 
     * @return
     */
    @Override
    public Optional<Reservation> add(Reservation reservation) {
        try {
            validator_reservation.validate(reservation);
            return repo_reservation.add(reservation);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return Optional.empty();
        }
    }

    /**
     * @param reservation 
     * @return
     */
    @Override
    public Optional<Reservation> update(Reservation reservation) {
        try {
            validator_reservation.validate(reservation);
            return repo_reservation.update(reservation);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return Optional.empty();
        }
    }

    /**
     * @param reservation 
     * @return
     */
    @Override
    public Optional<Reservation> delete(Reservation reservation) {
        try {
            validator_reservation.validate(reservation);
            return repo_reservation.delete(reservation);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return Optional.empty();
        }
    }

    /**
     * @param aLong 
     * @return
     */
    @Override
    public Optional<Reservation> find(Long aLong) {
        return repo_reservation.find(aLong);
    }

    /**
     * @return 
     */
    @Override
    public Iterable<Reservation> getAll() {
        return repo_reservation.getAll();
    }

    /**
     * @param account 
     * @param clientName
     * @param phoneNumber
     * @param tickets
     * @param trip
     * @return
     */
    @Override
    public Reservation createReservation(Account account, String clientName, String phoneNumber, Long tickets, Trip trip) {
       try{
           Reservation reservation = new Reservation(account, clientName, phoneNumber, tickets, trip);
           validator_reservation.validate(reservation);
           return reservation;
       }catch (Exception e){
           System.out.println(e.getMessage());
           return null;
       }
    }
}
