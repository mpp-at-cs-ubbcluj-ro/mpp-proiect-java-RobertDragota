package org.mpp2024;

public interface AppObserverInterface {
    void updateTrips(Iterable<Trip> list) throws AppException;

}
