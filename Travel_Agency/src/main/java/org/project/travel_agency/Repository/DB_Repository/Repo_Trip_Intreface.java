package org.project.travel_agency.Repository.DB_Repository;

import org.project.travel_agency.Domain.Account;
import org.project.travel_agency.Domain.Entity;
import org.project.travel_agency.Domain.Trip;

import java.util.Optional;

public interface Repo_Trip_Intreface extends RepoInterface<Long, Trip>{
    Optional<Trip> findByDestination(String destination);
    Iterable<Trip> filterTrips(String destination, int startHour, int finishHour);
}
