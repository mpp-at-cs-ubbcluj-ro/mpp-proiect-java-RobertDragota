package org.mpp2024.Utility.Validation;


import org.mpp2024.Trip;

public class Validator_Trip implements Validator<Trip>{
    /**
     * @param entity
     * @throws ValidException
     */
    @Override
    public void validate(Trip entity) throws ValidException {
        if(entity == null)
            throw new ValidException("Entity is null");
        if(entity.getDestination().isEmpty())
            throw new ValidException("Destination is empty");
        if(entity.getTransportCompany().isEmpty())
            throw new ValidException("Transport Company is empty");
        if(entity.getPrice() <=0)
            throw new ValidException("Price cannot be negative or zero");
        if(entity.getAvailableSeats() < 0)
            throw new ValidException("Seats is negative");
    }
}
