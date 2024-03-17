package org.project.travel_agency.Repository.DB_Repository;

import org.project.travel_agency.Domain.Reservation;
import org.project.travel_agency.Utility.DB_Utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.sql.*;
import java.util.Optional;

public class Repo_Reservation implements Repo_Reservation_Inreface<Long, Reservation>{
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

        try (Connection conn = DB_connection.getConnection();  // Ensure that DB_connection.connect() provides a valid database connection.
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
    public Optional<Reservation> find(Long aLong) {
        return Optional.empty();
    }

    @Override
    public Iterable<Reservation> getAll() {
        return null;
    }
}
