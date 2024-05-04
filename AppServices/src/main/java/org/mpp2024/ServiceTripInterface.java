package org.mpp2024;



import java.time.LocalDateTime;
import java.util.Optional;

public interface ServiceTripInterface extends ServiceInterface<Long, Trip> {

    Optional<Trip> findByDestination(String destination);
    Iterable<Trip> filterTrips(String destination, LocalDateTime date,LocalDateTime startHour, LocalDateTime finishHour);
}
