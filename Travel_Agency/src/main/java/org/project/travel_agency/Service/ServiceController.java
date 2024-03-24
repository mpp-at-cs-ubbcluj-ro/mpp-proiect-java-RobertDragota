package org.project.travel_agency.Service;

public class ServiceController {
    private final Service_Account accountService;
    private final Service_Reservation reservationService;
    private final Service_Trip tripService;

    public ServiceController(Service_Account accountService, Service_Reservation reservationService, Service_Trip tripService) {
        this.accountService = accountService;
        this.reservationService = reservationService;
        this.tripService = tripService;
    }

    public Service_Account getAccountService() {
        return accountService;
    }

    public Service_Reservation getReservationService() {
        return reservationService;
    }

    public Service_Trip getTripService() {
        return tripService;
    }
}
