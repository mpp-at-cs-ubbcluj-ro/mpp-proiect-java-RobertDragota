package org.project.travel_agency.Repository.DB_Repository;

import org.project.travel_agency.Domain.Trip;
import org.project.travel_agency.Repository.RepoInterface;
import org.project.travel_agency.Utility.DB_Utils;

import java.util.Optional;

public class Repo_Trip implements RepoInterface<Long, Trip>{
    private final DB_Utils DB_connection;

    public Repo_Trip(DB_Utils dbConnection) {
        DB_connection = dbConnection;
    }
    @Override
    public Optional<Trip> add(Trip trip) {
        return Optional.empty();
    }

    @Override
    public Optional<Trip> update(Trip trip) {
        return Optional.empty();
    }

    @Override
    public Optional<Trip> delete(Trip trip) {
        return Optional.empty();
    }

    @Override
    public Optional<Trip> find(Long aLong) {
        return Optional.empty();
    }

    @Override
    public Iterable<Trip> getAll() {
        return null;
    }
}
