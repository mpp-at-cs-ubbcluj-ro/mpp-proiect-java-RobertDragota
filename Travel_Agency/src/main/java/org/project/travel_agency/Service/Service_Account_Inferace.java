package org.project.travel_agency.Service;

import org.project.travel_agency.Domain.Account;

import java.util.Optional;

public interface Service_Account_Inferace extends ServiceInterface<Long, Account>{

    Optional<Account> findByUsername(String username);
    Account createAccount(String username,String password);
}
