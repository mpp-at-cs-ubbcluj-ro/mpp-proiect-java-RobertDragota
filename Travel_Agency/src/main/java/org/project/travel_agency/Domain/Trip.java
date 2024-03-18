package org.project.travel_agency.Domain;


import java.time.LocalDateTime;
import java.util.Date;

public class Trip extends Entity<Long>{
    private  String destination;
    private  String transportCompany;
    private  Long price;
    private  Long availableSeats;
    private  LocalDateTime date;
    private  LocalDateTime startHour;
    private  LocalDateTime finishHour;

    public Trip(String destination, String transportCompany, Long price, Long availableSeats, LocalDateTime date,  LocalDateTime startHour, LocalDateTime finishHour) {
        this.destination = destination;
        this.transportCompany = transportCompany;
        this.price = price;
        this.availableSeats = availableSeats;
        this.date = date;
        this.startHour = startHour;
        this.finishHour = finishHour;
    }

    public String getDestination() {
        return destination;
    }

    public String getTransportCompany() {
        return transportCompany;
    }

    public Long getPrice() {
        return price;
    }

    public Long getAvailableSeats() {
        return availableSeats;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public LocalDateTime getStartHour() {
        return startHour;
    }

    public LocalDateTime getFinishHour() {
        return finishHour;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public void setTransportCompany(String transportCompany) {
        this.transportCompany = transportCompany;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public void setAvailableSeats(Long availableSeats) {
        this.availableSeats = availableSeats;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public void setStartHour(LocalDateTime startHour) {
        this.startHour = startHour;
    }

    public void setFinishHour(LocalDateTime finishHour) {
        this.finishHour = finishHour;
    }
}
