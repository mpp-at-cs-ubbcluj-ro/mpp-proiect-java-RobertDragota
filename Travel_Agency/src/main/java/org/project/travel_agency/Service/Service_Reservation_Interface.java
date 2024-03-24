package org.project.travel_agency.Service;

import org.project.travel_agency.Domain.Account;
import org.project.travel_agency.Domain.Reservation;
import org.project.travel_agency.Domain.Trip;

public interface Service_Reservation_Interface extends ServiceInterface<Long, Reservation>{

    Reservation createReservation(Account account, String clientName, String phoneNumber, Long tickets, Trip trip);
}
