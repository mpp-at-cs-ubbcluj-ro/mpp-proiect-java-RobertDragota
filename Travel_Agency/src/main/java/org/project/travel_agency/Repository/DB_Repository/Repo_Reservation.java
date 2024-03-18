package org.project.travel_agency.Repository.DB_Repository;

import org.project.travel_agency.Domain.Client;
import org.project.travel_agency.Domain.Reservation;
import org.project.travel_agency.Domain.Trip;
import org.project.travel_agency.Utility.DB_Utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Repo_Reservation implements Repo_Reservation_Inreface<Long, Reservation> {
    private final DB_Utils DB_connection;
    private static final Logger logger = LoggerFactory.getLogger(Repo_Reservation.class);

    public Repo_Reservation(DB_Utils dbConnection) {
        DB_connection = dbConnection;
    }

    @Override
    public Optional<Reservation> add(Reservation reservation) {
        logger.info("Adding new reservation: {}", reservation);
        String SQL_INSERT = "INSERT INTO reservations (client_id, phone_number, tickets, trip_id) VALUES (?, ?, ?, ?) ";
        try (Connection conn = DB_connection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setLong(1, reservation.getClient().getId());
            pstmt.setString(2, reservation.getPhoneNumber());
            pstmt.setLong(3, reservation.getTickets());
            pstmt.setLong(4, reservation.getTrip().getId());
            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        reservation.setId(rs.getLong(1));
                        logger.info("Reservation added successfully with ID: {}", reservation.getId());
                        return Optional.of(reservation);
                    }
                }
            }
        } catch (SQLException e) {
            logger.error("Error adding reservation: {}", reservation, e);
        }
        return Optional.empty();
    }


    @Override
    public Optional<Reservation> update(Reservation reservation) {
        logger.info("Updating reservation: {}", reservation);
        String SQL_UPDATE = "UPDATE reservations SET client_id = ?, phone_number = ?, tickets = ?, trip_id = ? WHERE id = ?;";

        try (Connection conn = DB_connection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SQL_UPDATE)) {

            pstmt.setLong(1, reservation.getClient().getId());
            pstmt.setString(2, reservation.getPhoneNumber());
            pstmt.setLong(3, reservation.getTickets());
            pstmt.setLong(4, reservation.getTrip().getId());
            pstmt.setLong(5, reservation.getId());

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                logger.info("Reservation updated successfully: {}", reservation);
                return Optional.of(reservation);
            }
        } catch (SQLException e) {
            logger.error("Error updating reservation: {}", reservation, e);
        }
        return Optional.empty();
    }


    @Override
    public Optional<Reservation> delete(Reservation reservation) {
        logger.info("Deleting reservation: {}", reservation);
        String SQL_DELETE = "DELETE FROM reservations WHERE id = ?;";

        try (Connection conn = DB_connection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SQL_DELETE)) {

            pstmt.setLong(1, reservation.getId());

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                logger.info("Reservation deleted successfully: {}", reservation);
                return Optional.of(reservation);
            }
        } catch (SQLException e) {
            logger.error("Error deleting reservation: {}", reservation, e);
        }

        return Optional.empty();
    }

    @Override
    public Optional<Reservation> find(Long id) {
        logger.info("Finding reservation with ID: {}", id);
        String SQL_FIND = """
                    SELECT r.*, c.id AS client_id, c.name AS client_name, c.email,
                           t.id AS trip_id, t.destination, t.transport_company, t.price, t.available_seats, t.date, t.start_hour, t.finish_hour
                    FROM reservations r
                    INNER JOIN clients c ON r.client_id = c.id
                    INNER JOIN trips t ON r.trip_id = t.id
                    WHERE r.id = ?;
                """;

        try (Connection conn = DB_connection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SQL_FIND)) {

            pstmt.setLong(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                Client client = new Client(rs.getString("client_name"), rs.getString("email"));
                Trip trip = new Trip(
                        rs.getString("destination"), rs.getString("transport_company"), rs.getLong("price"),
                        rs.getLong("available_seats"), rs.getTimestamp("date").toLocalDateTime(),
                        rs.getTimestamp("start_hour").toLocalDateTime(), rs.getTimestamp("finish_hour").toLocalDateTime()
                );

                Reservation reservation = new Reservation(
                        client, rs.getString("phone_number"), rs.getLong("tickets"), trip
                );
                logger.info("Reservation found: {}", reservation);
                return Optional.of(reservation);
            } else {
                logger.info("No reservation found with ID: {}", id);
            }
        } catch (SQLException e) {
            logger.error("Error finding reservation with ID: {}", id, e);
        }
        return Optional.empty();
    }


    @Override
    public Iterable<Reservation> getAll() {
        logger.info("Retrieving all reservations");
        List<Reservation> reservations = new ArrayList<>();
        String SQL_GET_ALL = """
                    SELECT r.*, c.id AS client_id, c.name AS client_name, c.email,
                           t.id AS trip_id, t.destination, t.transport_company, t.price, t.available_seats, t.date, t.start_hour, t.finish_hour
                    FROM reservations r
                    INNER JOIN clients c ON r.client_id = c.id
                    INNER JOIN trips t ON r.trip_id = t.id;
                """;

        try (Connection conn = DB_connection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SQL_GET_ALL);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Client client = new Client(rs.getString("client_name"), rs.getString("email"));
                Trip trip = new Trip(
                        rs.getString("destination"), rs.getString("transport_company"), rs.getLong("price"),
                        rs.getLong("available_seats"), rs.getTimestamp("date").toLocalDateTime(),
                        rs.getTimestamp("start_hour").toLocalDateTime(), rs.getTimestamp("finish_hour").toLocalDateTime()
                );

                Reservation reservation = new Reservation(
                        client, rs.getString("phone_number"), rs.getLong("tickets"), trip
                );
                reservations.add(reservation);
            }
            logger.info("Retrieved {} reservations", reservations.size());
        } catch (SQLException e) {
            logger.error("Error retrieving all reservations", e);
        }
        return reservations;
    }


}
