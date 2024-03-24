package Test_Repo_Account;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.project.travel_agency.Domain.Account;
import org.project.travel_agency.Repository.DB_Repository.Repo_Account;
import org.project.travel_agency.Utility.DB_Utils;

import java.util.Optional;

public class Tests {


    static private final DB_Utils DB_connection = new DB_Utils("test_bd.config");
    static private final Repo_Account repoAccount = new Repo_Account(DB_connection);

    @Test
    @DisplayName("Add account test")
    public void testAdd() {

        Account account = new Account("Robert", "password123");

        Optional<Account> addedAccount = repoAccount.add(account);

        Assertions.assertTrue(addedAccount.isPresent(), "Account should be added");

        Account actualAccount = addedAccount.get();

        Assertions.assertEquals("Robert", actualAccount.getName(), "Client name in account should match");

        Assertions.assertEquals("password123", actualAccount.getPassword(), "Account password should match");

        repoAccount.delete(account);
    }

    @Test
    @DisplayName("Delete account test")
    public void testDelete() {

        Account account = new Account("Robert", "password123");
        account = repoAccount.add(account).orElse(null);

        Assertions.assertNotNull(account, "Added account should not be null");

        Optional<Account> deletedAccount = repoAccount.delete(account);

        Assertions.assertTrue(deletedAccount.isPresent(), "Account should be deleted");

        Optional<Account> foundAccount = repoAccount.find(account.getId());
        Assertions.assertFalse(foundAccount.isPresent(), "Deleted account should not be found");
    }

    @Test
    @DisplayName("Update account test")
    public void testUpdate() {

        Account account = new Account("Robert", "password123");
        account = repoAccount.add(account).orElse(null);

        Assertions.assertNotNull(account, "Added account should not be null");

        // Update account details
        account.setPassword("updatedPassword123");
        Optional<Account> updatedAccount = repoAccount.update(account);

        Assertions.assertTrue(updatedAccount.isPresent(), "Account should be updated");

        Account actualAccount = updatedAccount.get();
        Assertions.assertEquals("updatedPassword123", actualAccount.getPassword(), "Account password should be updated");

        repoAccount.delete(account);
    }

    @Test
    @DisplayName("Find account test")
    public void testFind() {

        Account account = new Account("Robert", "password123");
        account = repoAccount.add(account).orElse(null);

        Assertions.assertNotNull(account, "Added account should not be null");

        Optional<Account> foundAccount = repoAccount.find(account.getId());

        Assertions.assertTrue(foundAccount.isPresent(), "Account should be found");

        Account actualAccount = foundAccount.get();
        Assertions.assertEquals("Robert", actualAccount.getName(), "Found client name in account should match");
        Assertions.assertEquals("password123", actualAccount.getPassword(), "Found account password should match");
        repoAccount.delete(account);
    }

}
