package Test_Repo_Reservation;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.project.travel_agency.Domain.Account;

import org.project.travel_agency.Domain.Reservation;
import org.project.travel_agency.Domain.Trip;
import org.project.travel_agency.Repository.DB_Repository.Repo_Reservation;
import org.project.travel_agency.Utility.DB_Utils;

import java.time.LocalDateTime;
import java.util.Optional;

public class Tests {
    static private final DB_Utils DB_connection = new DB_Utils("test_bd.config");
    static private final Repo_Reservation reservationRepository = new Repo_Reservation(DB_connection);

    @Test
    @DisplayName("Add reservation test")
    public void testAddReservation() {
        Account account = new Account("Robert", "password123");
        account.setId(1L);
        Trip trip = new Trip("Paris", "TravelCo", 500L, 20L, LocalDateTime.now(), LocalDateTime.now().plusHours(2), LocalDateTime.now().plusHours(12));
        trip.setId(1L);
        Reservation reservation = new Reservation(account, "Robert","1234567890", 2L, trip);
        Optional<Reservation> reservation1 = reservationRepository.add(reservation);

        // Assertions
        Assertions.assertTrue(reservation1.isPresent(), "Reservation should be added");

        Reservation actualReservation = reservation1.get();
        Assertions.assertEquals("1234567890", actualReservation.getPhoneNumber(), "Reservation phone number should match");
        Assertions.assertEquals(2L, actualReservation.getTickets(), "Reservation tickets should match");
        reservationRepository.delete(actualReservation);
    }


    @Test
    @DisplayName("Delete reservation test")
    public void testDeleteReservation() {
        Account account = new Account("Robert", "password123");
        account.setId(1L);
        Trip trip = new Trip("Paris", "TravelCo", 500L, 20L, LocalDateTime.now(), LocalDateTime.now().plusHours(2), LocalDateTime.now().plusHours(12));
        trip.setId(1L);
        Reservation reservation = new Reservation(account, "Robert","1234567890", 2L, trip);
        reservation = reservationRepository.add(reservation).orElse(null);

        assert reservation != null;

        // Delete the reservation
        Optional<Reservation> deletedReservation = reservationRepository.delete(reservation);

        // Assertions
        Assertions.assertTrue(deletedReservation.isPresent(), "Reservation should be deleted");

        // Try to find the deleted reservation
        Optional<Reservation> foundReservation = reservationRepository.find(reservation.getId());
        Assertions.assertFalse(foundReservation.isPresent(), "Deleted reservation should not be found");
    }


    @Test
    @DisplayName("Update reservation test")
    public void testUpdateReservation() {
        // Predefined or mocked Client and Trip
        Account account = new Account("Robert", "password123");
        account.setId(1L);
        Trip trip = new Trip("Paris", "TravelCo", 500L, 20L, LocalDateTime.now(), LocalDateTime.now().plusHours(2), LocalDateTime.now().plusHours(12));
        trip.setId(1L);
        Reservation reservation = new Reservation(account, "Robert","1234567890", 2L, trip);
        reservation = reservationRepository.add(reservation).orElse(null);

        assert reservation != null;

        // Update the reservation details
        reservation.setPhoneNumber("0987654321");
        reservation.setTickets(3L);

        // Update the reservation in the repository
        Optional<Reservation> updatedReservation = reservationRepository.update(reservation);

        // Assertions
        Assertions.assertTrue(updatedReservation.isPresent(), "Reservation should be updated");

        Reservation actualReservation = updatedReservation.get();
        Assertions.assertEquals("0987654321", actualReservation.getPhoneNumber(), "Reservation phone number should be updated");
        Assertions.assertEquals(3L, actualReservation.getTickets(), "Reservation tickets should be updated");
        reservationRepository.delete(reservation);
    }


    @Test
    @DisplayName("Find reservation test")
    public void testFindReservation() {
        Account account = new Account("Robert", "password123");
        account.setId(1L);
        Trip trip = new Trip("Paris", "TravelCo", 500L, 20L, LocalDateTime.now(), LocalDateTime.now().plusHours(2), LocalDateTime.now().plusHours(12));
        trip.setId(1L);
        Reservation reservation = new Reservation(account, "Robert","1234567890", 2L, trip);
        reservation = reservationRepository.add(reservation).orElse(null);

        assert reservation != null;

        // Find the reservation
        Optional<Reservation> foundReservation = reservationRepository.find(reservation.getId());

        // Assertions
        Assertions.assertTrue(foundReservation.isPresent(), "Reservation should be found");

        Reservation actualReservation = foundReservation.get();
        Assertions.assertEquals("1234567890", actualReservation.getPhoneNumber(), "Found reservation phone number should match");
        Assertions.assertEquals(2L, actualReservation.getTickets(), "Found reservation tickets should match");
        reservationRepository.delete(reservation);
    }


}
