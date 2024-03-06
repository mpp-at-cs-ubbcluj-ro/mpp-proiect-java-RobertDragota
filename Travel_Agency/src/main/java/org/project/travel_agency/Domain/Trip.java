package org.project.travel_agency.Domain;


import java.util.Date;

public class Trip extends Entity<Long>{
    private final String destination;
    private final String transportCompany;
    private final Long price;
    private final Long availableSeats;
    private final Date date;
    private final Date startHour;
    private final Date finishHour;

    public Trip(String destination, String transportCompany, Long price, Long availableSeats, Date date, Date startHour, Date finishHour) {
        this.destination = destination;
        this.transportCompany = transportCompany;
        this.price = price;
        this.availableSeats = availableSeats;
        this.date = date;
        this.startHour = startHour;
        this.finishHour = finishHour;
    }
}
