package org.project.travel_agency.Repository.DB_Repository;

import org.project.travel_agency.Domain.Account;
import org.project.travel_agency.Domain.Client;
import org.project.travel_agency.Domain.Tuple;
import org.project.travel_agency.Utility.DB_Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.Optional;

public class Repo_Account implements Repo_Account_Interface<Tuple<Long, String>, Account> {

    private final DB_Utils DB_connection;
    private static final Logger logger = LoggerFactory.getLogger(Repo_Client.class);

    public Repo_Account(DB_Utils dbConnection) {
        DB_connection = dbConnection;
    }

    @Override
    public Optional<Account> add(Account account) {
        logger.info("Adding new account: {}", account);
        String SQL_INSERT = "INSERT INTO account (client_id, password) VALUES (?, ?) ";
        try (Connection conn = DB_connection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setLong(1, account.getClient().getId());
            pstmt.setString(2, account.getPassword());

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        account.setId(new Tuple<>(rs.getLong(1), account.getPassword()));
                        logger.info("Account added successfully with client ID: {}", account.getId().getFirst());
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
        String SQL_UPDATE = "UPDATE account SET password=? WHERE client_id=?";
        try (Connection conn = DB_connection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SQL_UPDATE)) {

            pstmt.setString(1, account.getPassword());
            pstmt.setLong(2, account.getClient().getId());

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
        String SQL_DELETE = "DELETE FROM account WHERE client_id=?";
        try (Connection conn = DB_connection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SQL_DELETE)) {

            pstmt.setLong(1, account.getClient().getId());

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
    public Optional<Account> find(Tuple<Long, String> tuple) {
        logger.info("Finding account with client ID: {}", tuple.getFirst());

        String SQL_FIND = "SELECT a.*, c.id as client_id, c.name, c.email " +
                "FROM account a " +
                "INNER JOIN clients c ON a.client_id = c.id " +
                "WHERE a.client_id=? AND a.password=?";

        try (Connection conn = DB_connection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SQL_FIND)) {

            pstmt.setLong(1, tuple.getFirst());
            pstmt.setString(2, tuple.getSecond());

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                Client client = new Client(
                        rs.getString("name"),
                        rs.getString("email")
                );
                client.setId(rs.getLong("client_id"));
                Account account = new Account(
                        client,
                        rs.getString("password")
                );
                logger.info("Account found: {}", account);
                return Optional.of(account);
            } else {
                logger.info("No account found with client ID: {}", tuple.getFirst());
            }
        } catch (SQLException e) {
            logger.error("Error finding account with client ID: {}", tuple.getFirst(), e);
        }
        return Optional.empty();
    }


    @Override
    public Iterable<Account> getAll() {
        return null;
    }
}
