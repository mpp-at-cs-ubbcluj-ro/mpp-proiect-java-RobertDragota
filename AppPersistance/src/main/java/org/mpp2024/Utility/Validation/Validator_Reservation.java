package org.mpp2024.Utility.Validation;


import org.mpp2024.Reservation;

public class Validator_Reservation implements Validator<Reservation>
{
    /**
     * @param entity
     * @throws ValidException
     */
    @Override
    public void validate(Reservation entity) throws ValidException
    {
        if(entity == null)
            throw new ValidException("Entity is null");
        if(entity.getAccount().getName().isEmpty())
            throw new ValidException("Tourist name is empty");
        if(entity.getPhoneNumber().isEmpty())
            throw new ValidException("Phone number is empty");
        if(entity.getTickets() <= 0)
            throw new ValidException("Number of tickets is invalid");
        if(entity.getAccount().getName().length() < 3)
            throw new ValidException("Tourist name is too short");
        if(entity.getPhoneNumber().length() < 10)
            throw new ValidException("Phone number is too short");
    }
}
