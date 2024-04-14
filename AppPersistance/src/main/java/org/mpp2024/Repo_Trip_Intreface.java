package org.mpp2024;


import java.util.Optional;

public interface Repo_Trip_Intreface extends RepoInterface<Long, Trip>{
    Optional<Trip> findByDestination(String destination);
    Iterable<Trip> filterTrips(String destination, int startHour, int finishHour);
}
