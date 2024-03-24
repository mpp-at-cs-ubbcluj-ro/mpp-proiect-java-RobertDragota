package org.project.travel_agency.Repository.DB_Repository;

import org.project.travel_agency.Domain.Account;

import org.project.travel_agency.Utility.DB_Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Repo_Account implements Repo_Account_Interface {

    private final DB_Utils DB_connection;
    private static final Logger logger = LoggerFactory.getLogger(Repo_Account.class);

    public Repo_Account(DB_Utils dbConnection) {
        DB_connection = dbConnection;
    }

    @Override
    public Optional<Account> add(Account account) {
        logger.info("Adding new account: {}", account);
        String SQL_INSERT = "INSERT INTO account (name, password) VALUES (?, ?) ";
        try (Connection conn = DB_connection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, account.getName());
            pstmt.setString(2, account.getPassword());

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        account.setId(rs.getLong(1));
                        logger.info("Account added successfully with client ID: {}", account.getId());
                        return Optional.of(account);
                    }
                }
            }
        } catch (SQLException e) {
            logger.error("Error adding account: {}", account, e);
        }
        return Optional.empty();
    }

    @Override
    public Optional<Account> update(Account account) {
        logger.info("Updating account: {}", account);
        String SQL_UPDATE = "UPDATE account SET name=? ,password=? WHERE id=?";
        try (Connection conn = DB_connection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SQL_UPDATE)) {

            pstmt.setString(1, account.getName());
            pstmt.setString(2, account.getPassword());
            pstmt.setLong(3, account.getId());

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                logger.info("Account updated successfully: {}", account);
                return Optional.of(account);
            }
        } catch (SQLException e) {
            logger.error("Error updating account: {}", account, e);
        }
        return Optional.empty();
    }

    @Override
    public Optional<Account> delete(Account account) {
        logger.info("Deleting account: {}", account);
        String SQL_DELETE = "DELETE FROM account WHERE id=?";
        try (Connection conn = DB_connection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SQL_DELETE)) {

            pstmt.setLong(1, account.getId());

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                logger.info("Account deleted successfully: {}", account);
                return Optional.of(account);
            }
        } catch (SQLException e) {
            logger.error("Error deleting account: {}", account, e);
        }
        return Optional.empty();
    }

    @Override
    public Optional<Account> find(Long id) {
        logger.info("Finding account with ID: {}", id);

        String SQL_FIND = "SELECT a.*" +
                "FROM account a " +
                "WHERE a.id=? ";

        try (Connection conn = DB_connection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SQL_FIND)) {

            pstmt.setLong(1, id);


            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                Account account = new Account(
                        rs.getString("name"),
                        rs.getString("password")
                );
                logger.info("Account found: {}", account);
                return Optional.of(account);
            } else {
                logger.info("No account found with  ID: {}", id);
            }
        } catch (SQLException e) {
            logger.error("Error finding account with  ID: {}", id, e);
        }
        return Optional.empty();
    }


    @Override
    public Iterable<Account> getAll() {
        logger.info("Retrieving all accounts");

        String SQL_GET_ALL = "SELECT * FROM account";

        List<Account> accounts = new ArrayList<>();

        try (Connection conn = DB_connection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SQL_GET_ALL);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Account account = new Account(

                        rs.getString("name"),
                        rs.getString("password")
                );
                account.setId(rs.getLong("id"));
                accounts.add(account);
            }
            logger.info("Accounts retrieved: {}", accounts);
        } catch (SQLException e) {
            logger.error("Error retrieving all accounts", e);
        }
        return accounts;
    }

    /**
     * @param username
     * @return
     */
    @Override
    public Optional<Account> findByUsername(String username) {
        logger.info("Finding account with username: {}", username);

        String SQL_FIND = "SELECT a.*" +
                "FROM account a " +
                "WHERE a.name=? ";

        try (Connection conn = DB_connection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SQL_FIND)) {

            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                Account account = new Account(
                        rs.getString("name"),
                        rs.getString("password")
                );
                account.setId(rs.getLong("id"));
                logger.info("Account found: {}", account);
                return Optional.of(account);
            } else {
                logger.info("No account found with  username: {}", username);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }
}
