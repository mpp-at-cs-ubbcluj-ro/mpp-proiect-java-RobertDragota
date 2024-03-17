package org.project.travel_agency.Domain;

public class Reservation extends Entity<Long> {
    private final Client client;
    private final String phoneNumber;
    private final Long tickets;
    private final Trip trip;

    public Reservation(Client client, String phoneNumber, Long tickets, Trip trip) {
        this.client = client;
        this.phoneNumber = phoneNumber;
        this.tickets = tickets;
        this.trip = trip;
    }

    public Client getClient() {
        return client;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Long getTickets() {
        return tickets;
    }

    public Trip getTrip() {
        return trip;
    }
}
