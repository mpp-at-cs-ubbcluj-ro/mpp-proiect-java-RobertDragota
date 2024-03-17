package org.project.travel_agency.Repository.DB_Repository;

import org.project.travel_agency.Domain.Account;
import org.project.travel_agency.Domain.Tuple;
import org.project.travel_agency.Utility.DB_Utils;

import java.util.Optional;

public class Repo_Account implements Repo_Account_Interface<Tuple<Long, String>, Account> {

    private final DB_Utils DB_connection;

    public Repo_Account(DB_Utils dbConnection) {
        DB_connection = dbConnection;
    }

    @Override
    public Optional<Account> add(Account account) {
        return Optional.empty();
    }

    @Override
    public Optional<Account> update(Account account) {
        return Optional.empty();
    }

    @Override
    public Optional<Account> delete(Account account) {
        return Optional.empty();
    }

    @Override
    public Optional<Account> find(Tuple<Long, String> longStringTuple) {
        return Optional.empty();
    }


    @Override
    public Iterable<Account> getAll() {
        return null;
    }
}
