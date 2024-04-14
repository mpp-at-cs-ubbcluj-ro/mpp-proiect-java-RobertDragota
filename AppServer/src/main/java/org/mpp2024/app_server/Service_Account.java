package org.mpp2024.app_server;

import org.mpp2024.Account;
import org.mpp2024.Repo_Account;
import org.mpp2024.ServiceAccountInferace;
import org.mpp2024.Utility.Validation.ValidException;
import org.mpp2024.Utility.Validation.Validator_Account;


import java.util.Optional;

public class Service_Account implements ServiceAccountInferace {

    private final Repo_Account repo_account;
    private final Validator_Account validator_account;

    public Service_Account(Repo_Account repo_account, Validator_Account validator_account) {
        this.repo_account = repo_account;
        this.validator_account = validator_account;
    }

    /**
     * @param account
     * @return
     */
    @Override
    public Optional<Account> add(Account account) {
        try {
            validator_account.validate(account);
            if (repo_account.findByUsername(account.getName()).isEmpty())
                return repo_account.add(account);
            else {
                throw new ValidException("Username already exists!");
            }
        } catch (ValidException e) {
            System.out.println(e.getMessage());
        }
        return Optional.empty();
    }

    /**
     * @param account
     * @return
     */
    @Override
    public Optional<Account> update(Account account) {
        try {
            validator_account.validate(account);
            return repo_account.update(account);
        } catch (ValidException e) {
            System.out.println(e.getMessage());
            return Optional.empty();
        }
    }

    /**
     * @param account
     * @return
     */
    @Override
    public Optional<Account> delete(Account account) {
        try {
            validator_account.validate(account);
            return repo_account.delete(account);
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
    public Optional<Account> find(Long aLong) {
        return repo_account.find(aLong);
    }

    /**
     * @return
     */
    @Override
    public Iterable<Account> getAll() {
        return repo_account.getAll();
    }

    /**
     * @param username
     * @return
     */
    @Override
    public Optional<Account> findByUsername(String username) {
        return repo_account.findByUsername(username);
    }

    /**
     * @param username 
     * @param password
     * @return
     */
    @Override
    public Account createAccount(String username, String password) {
        try {
            Account account = new Account(username, password);
            validator_account.validate(account);
            return account;
        } catch (ValidException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}
