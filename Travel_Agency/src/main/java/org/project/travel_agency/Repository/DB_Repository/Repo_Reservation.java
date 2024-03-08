package org.project.travel_agency.Repository.DB_Repository;

import org.project.travel_agency.Domain.Reservation;
import org.project.travel_agency.Repository.RepoInterface;
import org.project.travel_agency.Utility.DB_Utils;

import java.util.Optional;

public class Repo_Reservation implements RepoInterface<Long, Reservation>{
    private final DB_Utils DB_connection;;

    public Repo_Reservation(DB_Utils dbConnection) {
        DB_connection = dbConnection;
    }

    @Override
    public Optional<Reservation> add(Reservation reservation) {
        return Optional.empty();
    }

    @Override
    public Optional<Reservation> update(Reservation reservation) {
        return Optional.empty();
    }

    @Override
    public Optional<Reservation> delete(Reservation reservation) {
        return Optional.empty();
    }

    @Override
    public Optional<Reservation> find(Long aLong) {
        return Optional.empty();
    }

    @Override
    public Iterable<Reservation> getAll() {
        return null;
    }
}
