package org.mpp2024;


import java.util.Optional;

public interface Repo_Account_Interface extends RepoInterface<Long, Account>{
    Optional<Account> findByUsername(String username);


}
