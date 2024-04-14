package org.mpp2024.Utility.Validation;


import org.mpp2024.Account;

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
