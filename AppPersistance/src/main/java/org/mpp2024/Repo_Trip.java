package org.mpp2024;

import org.mpp2024.Utility.DB_Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Repo_Trip implements Repo_Trip_Intreface {
    private final DB_Utils DB_connection;
    private static final Logger logger = LoggerFactory.getLogger(Repo_Trip.class);

    public Repo_Trip(DB_Utils dbConnection) {
        DB_connection = dbConnection;
    }

    @Override
    public Optional<Trip> add(Trip trip) {
        logger.info("Adding new trip: {}", trip);
        String SQL_INSERT = "INSERT INTO trips (destination, transport_company, price, available_seats, trip_date, start_hour, finish_hour) VALUES (?, ?, ?, ?, ?, ?, ?) ";
        try (Connection conn = DB_connection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, trip.getDestination());
            pstmt.setString(2, trip.getTransportCompany());
            pstmt.setLong(3, trip.getPrice());
            pstmt.setLong(4, trip.getAvailableSeats());
            pstmt.setTimestamp(5, Timestamp.valueOf(trip.getDate()));
            pstmt.setTimestamp(6, Timestamp.valueOf(trip.getStartHour()));
            pstmt.setTimestamp(7, Timestamp.valueOf(trip.getFinishHour()));

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        trip.setId(rs.getLong(1));
                        logger.info("Trip added successfully with ID: {}", trip.getId());
                        return Optional.of(trip);
                    }
                }
            }
        } catch (SQLException e) {
            logger.error("Error adding trip: {}", trip, e);
        }
        return Optional.empty();
    }


    @Override
    public Optional<Trip> update(Trip trip) {
        logger.info("Updating trip: {}", trip);
        String SQL_UPDATE = "UPDATE trips SET destination = ?, transport_company = ?, price = ?, available_seats = ?, trip_date = ?, start_hour = ?, finish_hour = ? WHERE id = ?;";
        try (Connection conn = DB_connection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SQL_UPDATE)) {

            pstmt.setString(1, trip.getDestination());
            pstmt.setString(2, trip.getTransportCompany());
            pstmt.setLong(3, trip.getPrice());
            pstmt.setLong(4, trip.getAvailableSeats());
            pstmt.setTimestamp(5, Timestamp.valueOf(trip.getDate()));
            pstmt.setTimestamp(6, Timestamp.valueOf(trip.getStartHour()));
            pstmt.setTimestamp(7, Timestamp.valueOf(trip.getFinishHour()));
            pstmt.setLong(8, trip.getId());

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                logger.info("Trip updated successfully: {}", trip);
                return Optional.of(trip);
            }
        } catch (SQLException e) {
            logger.error("Error updating trip: {}", trip, e);
        }
        return Optional.empty();
    }

    @Override
    public Optional<Trip> delete(Trip trip) {
        logger.info("Deleting trip: {}", trip);
        String SQL_DELETE = "DELETE FROM trips WHERE id = ?;";
        try (Connection conn = DB_connection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SQL_DELETE)) {

            pstmt.setLong(1, trip.getId());

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                logger.info("Trip deleted successfully: {}", trip);
                return Optional.of(trip);
            }
        } catch (SQLException e) {
            logger.error("Error deleting trip: {}", trip, e);
        }
        return Optional.empty();
    }


    @Override
    public Optional<Trip> find(Long id) {
        logger.info("Finding trip with ID: {}", id);
        String SQL_FIND = "SELECT * FROM trips WHERE id = ?;";
        try (Connection conn = DB_connection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SQL_FIND)) {

            pstmt.setLong(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                Trip trip = new Trip(
                        rs.getString("destination"),
                        rs.getString("transport_company"),
                        rs.getLong("price"),
                        rs.getLong("available_seats"),
                        rs.getTimestamp("date").toLocalDateTime(),
                        rs.getTimestamp("start_hour").toLocalDateTime(),
                        rs.getTimestamp("finish_hour").toLocalDateTime()
                );
                trip.setId(rs.getLong("id"));
                logger.info("Trip found: {}", trip);
                return Optional.of(trip);
            } else {
                logger.info("No trip found with ID: {}", id);
            }
        } catch (SQLException e) {
            logger.error("Error finding trip with ID: {}", id, e);
        }
        return Optional.empty();
    }

    @Override
    public Iterable<Trip> getAll() {
        logger.info("Retrieving all trips");
        List<Trip> trips = new ArrayList<>();
        String SQL_GET_ALL = "SELECT * FROM trips";
        try (Connection conn = DB_connection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SQL_GET_ALL);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Trip trip = new Trip(
                        rs.getString("destination"),
                        rs.getString("transport_company"),
                        rs.getLong("price"),
                        rs.getLong("available_seats"),
                        rs.getTimestamp("trip_date").toLocalDateTime(),
                        rs.getTimestamp("start_hour").toLocalDateTime(),
                        rs.getTimestamp("finish_hour").toLocalDateTime()
                );
                trip.setId(rs.getLong("id"));
                trips.add(trip);
            }
            logger.info("Retrieved {} trips", trips.size());
        } catch (SQLException e) {
            logger.error("Error retrieving all trips", e);
        }
        return trips;
    }

    /**
     * @param destination
     * @return
     */
    @Override
    public Optional<Trip> findByDestination(String destination) {
        logger.info("Finding trip with destination: {}", destination);
        String SQL_FIND = "SELECT * FROM trips WHERE destination = ?;";
        try (Connection conn = DB_connection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SQL_FIND)) {

            pstmt.setString(1, destination);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                Trip trip = new Trip(
                        rs.getString("destination"),
                        rs.getString("transport_company"),
                        rs.getLong("price"),
                        rs.getLong("available_seats"),
                        rs.getTimestamp("date").toLocalDateTime(),
                        rs.getTimestamp("start_hour").toLocalDateTime(),
                        rs.getTimestamp("finish_hour").toLocalDateTime()
                );
                trip.setId(rs.getLong("id"));
                logger.info("Trip found: {}", trip);
                return Optional.of(trip);
            } else {
                logger.info("No trip found with destination: {}", destination);
            }
        } catch (SQLException e) {
            logger.error("Error finding trip with destination: {}", destination, e);
        }
        return Optional.empty();
    }

    /**
     * @param destination
     * @param startHour
     * @param finishHour
     * @return
     */
    @Override
    public Iterable<Trip> filterTrips(String destination,LocalDateTime date, LocalDateTime startHour, LocalDateTime finishHour) {
        logger.info("Filtering trips with destination: {}, startHour: {}, finishHour: {}", destination, startHour, finishHour);
        List<Trip> trips = new ArrayList<>();

        // SQL query modified to compare only the hour part
        String SQL_FILTER = "SELECT * FROM trips WHERE destination = ? AND  trip_date = ? AND start_hour >= ? AND finish_hour <= ?;";

        try (Connection conn = DB_connection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SQL_FILTER)) {

            pstmt.setString(1, destination);
            pstmt.setTimestamp(2, Timestamp.valueOf(date));
            pstmt.setTimestamp(3, Timestamp.valueOf(startHour));
            pstmt.setTimestamp(4, Timestamp.valueOf(finishHour));
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Trip trip = new Trip(
                        rs.getString("destination"),
                        rs.getString("transport_company"),
                        rs.getLong("price"),
                        rs.getLong("available_seats"),
                        rs.getTimestamp("trip_date").toLocalDateTime(),
                        rs.getTimestamp("start_hour").toLocalDateTime(),
                        rs.getTimestamp("finish_hour").toLocalDateTime()
                );
                trip.setId(rs.getLong("id"));
                trips.add(trip);
            }
            logger.info("Retrieved {} trips", trips.size());
        } catch (SQLException e) {
            e.printStackTrace();
            logger.error("Error filtering trips with destination: {}, startHour: {}, finishHour: {}", destination, startHour, finishHour, e);
        }
        return trips;
    }
}

