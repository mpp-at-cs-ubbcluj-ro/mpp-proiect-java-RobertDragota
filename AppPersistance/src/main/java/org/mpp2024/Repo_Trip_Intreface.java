package org.mpp2024;


import java.time.LocalDateTime;
import java.util.Optional;

public interface Repo_Trip_Intreface extends RepoInterface<Long, Trip>{
    Optional<Trip> findByDestination(String destination);
    Iterable<Trip> filterTrips(String destination, LocalDateTime date,LocalDateTime startHour, LocalDateTime finishHour);
}
