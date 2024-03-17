package org.project.travel_agency.Repository.DB_Repository;

import org.project.travel_agency.Domain.Client;
import org.project.travel_agency.Utility.DB_Utils;

import java.sql.*;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Repo_Client implements Repo_Client_Interface<Long, Client> {
    private final DB_Utils DB_connection;
    private static final Logger logger = LoggerFactory.getLogger(Repo_Client.class);

    public Repo_Client(DB_Utils dbConnection) {
        DB_connection = dbConnection;
    }

    @Override
    public Optional<Client> add(Client client) {
        String SQL_INSERT = "INSERT INTO clients(name, email) VALUES (?, ?);";
        logger.info("Adding a new client: {}", client);

        try (Connection conn = DB_connection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, client.getName());
            pstmt.setString(2, client.getEmail());
            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        long id = rs.getLong("id");
                        Client newClient = new Client(client.getName(), client.getEmail());
                        newClient.setId(id);
                        logger.info("New client added successfully: {}", newClient);
                        return Optional.of(newClient);
                    }
                }
            }
        } catch (SQLException ex) {
            logger.error("Error adding client: {}", client, ex);
        }
        return Optional.empty();
    }

    @Override
    public Optional<Client> update(Client client) {
        String SQL_UPDATE = "UPDATE clients SET name = ?, email = ? WHERE id = ?";

        try (Connection conn = DB_connection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SQL_UPDATE)) {

            pstmt.setString(1, client.getName());
            pstmt.setString(2, client.getEmail());
            pstmt.setLong(3, client.getId());

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                logger.info("Client updated successfully: {}", client);
                return Optional.of(client);
            }
        } catch (SQLException ex) {
            logger.error("Error updating client: {}", client, ex);
        }

        return Optional.empty();
    }


    @Override
    public Optional<Client> delete(Client client) {
        String SQL_DELETE = "DELETE FROM clients WHERE id = ?";

        try (Connection conn = DB_connection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SQL_DELETE)) {


            pstmt.setLong(1, client.getId());

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                logger.info("Client deleted successfully: {}", client);
                return Optional.of(client);
            }
        } catch (SQLException ex) {
            logger.error("Error deleting client: {}", client, ex);
        }

        return Optional.empty();
    }


    @Override
    public Optional<Client> find(Long clientId) {
        String SQL_FIND = "SELECT id, name, email FROM clients WHERE id = ?";

        try (Connection conn = DB_connection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SQL_FIND)) {

            pstmt.setLong(1, clientId);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                Long id = rs.getLong("id");
                String name = rs.getString("name");
                String email = rs.getString("email");
                Client client = new Client(name, email);
                client.setId(id);
                logger.info("Client found: {}", client);
                return Optional.of(client);
            }
        } catch (SQLException ex) {
            logger.error("Error finding client with ID: {}", clientId, ex);
        }

        return Optional.empty();
    }


    @Override
    public Iterable<Client> getAll() {
        return null;
    }
}
