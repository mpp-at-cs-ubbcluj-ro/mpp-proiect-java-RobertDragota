package org.project.travel_agency.Domain;

public class Reservation extends Entity<Long>{
    private final User user;
    private final String phoneNumber;
    private final Long tickets;
    private final Long tripId;

    public Reservation(User user, String phoneNumber, Long tickets, Long tripId) {
        this.user = user;
        this.phoneNumber = phoneNumber;
        this.tickets = tickets;
        this.tripId = tripId;
    }
}
