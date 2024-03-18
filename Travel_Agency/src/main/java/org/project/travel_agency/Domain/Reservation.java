package org.project.travel_agency.Domain;

public class Reservation extends Entity<Long> {
    private Client client;
    private String phoneNumber;
    private Long tickets;
    private Trip trip;

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

    public void setClient(Client client) {
        this.client = client;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setTickets(Long tickets) {
        this.tickets = tickets;
    }

    public void setTrip(Trip trip) {
        this.trip = trip;
    }
}
