package org.project.travel_agency.Repository.DB_Repository;

import org.project.travel_agency.Domain.Account;
import org.project.travel_agency.Domain.Entity;
import org.project.travel_agency.Domain.Tuple;

import java.util.Optional;

public interface Repo_Account_Interface extends RepoInterface<Long, Account>{
    Optional<Account> findByUsername(String username);


}
