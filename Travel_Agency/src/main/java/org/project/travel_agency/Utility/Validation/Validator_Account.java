package org.project.travel_agency.Utility.Validation;

import org.project.travel_agency.Domain.Account;

public class Validator_Account implements Validator<Account> {
    /**
     * @param entity
     * @throws ValidException
     */
    @Override
    public void validate(Account entity) throws ValidException {
        if(entity == null)
            throw new ValidException("Entity is null");
        if(entity.getName().isEmpty())
            throw new ValidException("Username is empty");
        if(entity.getPassword().isEmpty())
            throw new ValidException("Password is empty");
    }
}
