package org.mpp2024;


public interface ServiceReservationInterface extends ServiceInterface<Long, Reservation>{

    Reservation createReservation(Account account, String clientName, String phoneNumber, Long tickets, Trip trip);
}
