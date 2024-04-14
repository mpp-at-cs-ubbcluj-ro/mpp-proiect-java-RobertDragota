package org.mpp2024;


import java.util.Optional;

public interface ServiceAccountInferace extends ServiceInterface<Long, Account>{

    Optional<Account> findByUsername(String username);
    Account createAccount(String username,String password);
}
