package org.project.travel_agency.Service;

import org.project.travel_agency.Domain.Trip;

import java.util.Optional;

public interface Serive_Trip_Interface extends ServiceInterface<Long, Trip> {

    Optional<Trip> findByDestination(String destination);
    Iterable<Trip> filterTrips(String destination, int startHour, int finishHour);
}
