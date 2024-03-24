package org.project.travel_agency.Domain;

public class Reservation extends Entity<Long> {
    private Account account;
    private String clientName;
    private String phoneNumber;
    private Long tickets;
    private Trip trip;

    public Reservation(Account account, String clientName, String phoneNumber, Long tickets, Trip trip) {
        this.account = account;
        this.clientName = clientName;
        this.phoneNumber = phoneNumber;
        this.tickets = tickets;
        this.trip = trip;
    }

    public Account getAccount() {
        return account;
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

    public void setAccount(Account account) {
        this.account = account;
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

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }
}
