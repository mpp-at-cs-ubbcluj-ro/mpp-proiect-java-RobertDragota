package org.mpp2024;



import java.util.Optional;

public interface ServiceTripInterface extends ServiceInterface<Long, Trip> {

    Optional<Trip> findByDestination(String destination);
    Iterable<Trip> filterTrips(String destination, int startHour, int finishHour);
}
