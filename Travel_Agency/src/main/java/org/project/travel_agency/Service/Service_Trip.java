package org.project.travel_agency.Service;

import org.project.travel_agency.Domain.Trip;
import org.project.travel_agency.Repository.DB_Repository.Repo_Trip;
import org.project.travel_agency.Utility.Validation.ValidException;
import org.project.travel_agency.Utility.Validation.Validator_Trip;

import java.util.Optional;

public class Service_Trip implements Serive_Trip_Interface {

    private final Repo_Trip repo_trip;
    private final Validator_Trip validator_trip;

    public Service_Trip(Repo_Trip repo_trip, Validator_Trip validator_trip) {
        this.repo_trip = repo_trip;
        this.validator_trip = validator_trip;
    }

    /**
     * @param trip
     * @return
     */
    @Override
    public Optional<Trip> add(Trip trip) {
        try {
            validator_trip.validate(trip);
            return repo_trip.add(trip);
        } catch (ValidException e) {
            System.out.println(e.getMessage());
            return Optional.empty();
        }
    }

    /**
     * @param trip
     * @return
     */
    @Override
    public Optional<Trip> update(Trip trip) {
        try {
            validator_trip.validate(trip);
            return repo_trip.update(trip);
        } catch (ValidException e) {
            System.out.println(e.getMessage());
            return Optional.empty();
        }
    }

    /**
     * @param trip
     * @return
     */
    @Override
    public Optional<Trip> delete(Trip trip) {
        try {
            validator_trip.validate(trip);
            return repo_trip.delete(trip);
        } catch (ValidException e) {
            System.out.println(e.getMessage());
            return Optional.empty();
        }
    }

    /**
     * @param aLong
     * @return
     */
    @Override
    public Optional<Trip> find(Long aLong) {
        return repo_trip.find(aLong);
    }

    /**
     * @return
     */
    @Override
    public Iterable<Trip> getAll() {
        return repo_trip.getAll();
    }

    /**
     * @param destination
     * @return
     */
    @Override
    public Optional<Trip> findByDestination(String destination) {
        return repo_trip.findByDestination(destination);
    }

    /**
     * @param destination 
     * @param startHour
     * @param finishHour
     * @return
     */
    @Override
    public Iterable<Trip> filterTrips(String destination, int startHour, int finishHour) {
        return repo_trip.filterTrips(destination, startHour, finishHour);
    }
}
