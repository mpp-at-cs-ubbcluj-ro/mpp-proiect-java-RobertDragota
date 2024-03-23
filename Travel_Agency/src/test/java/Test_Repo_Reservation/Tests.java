package Test_Repo_Reservation;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.project.travel_agency.Domain.Client;
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
        Client client = new Client("Robert", "dragotarobert10@gmail.com");
        client.setId(2L);
        Trip trip = new Trip("Paris", "TravelCo", 500L, 20L, LocalDateTime.now(), LocalDateTime.now().plusHours(2), LocalDateTime.now().plusHours(12));
        trip.setId(2L);
        Reservation reservation = new Reservation(client, "1234567890", 2L, trip);
        reservation = reservationRepository.add(reservation).orElse(null);

        assert reservation != null;


        // Add the reservation
        Optional<Reservation> addedReservation = reservationRepository.add(reservation);

        // Assertions
        Assertions.assertTrue(addedReservation.isPresent(), "Reservation should be added");

        Reservation actualReservation = addedReservation.get();
        Assertions.assertEquals("1234567890", actualReservation.getPhoneNumber(), "Reservation phone number should match");
        Assertions.assertEquals(2L, actualReservation.getTickets(), "Reservation tickets should match");
        reservationRepository.delete(reservation);
    }


    @Test
    @DisplayName("Delete reservation test")
    public void testDeleteReservation() {
        Client client = new Client("Robert", "dragotarobert10@gmail.com");
        client.setId(2L);
        Trip trip = new Trip("Paris", "TravelCo", 500L, 20L, LocalDateTime.now(), LocalDateTime.now().plusHours(2), LocalDateTime.now().plusHours(12));
        trip.setId(2L);
        Reservation reservation = new Reservation(client, "1234567890", 2L, trip);
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
        Client client = new Client("Robert", "dragotarobert10@gmail.com");
        client.setId(2L);
        Trip trip = new Trip("Paris", "TravelCo", 500L, 20L, LocalDateTime.now(), LocalDateTime.now().plusHours(2), LocalDateTime.now().plusHours(12));
        trip.setId(2L);
        Reservation reservation = new Reservation(client, "1234567890", 2L, trip);
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
        Client client = new Client("Robert", "dragotarobert10@gmail.com");
        client.setId(2L);
        Trip trip = new Trip("Paris", "TravelCo", 500L, 20L, LocalDateTime.now(), LocalDateTime.now().plusHours(2), LocalDateTime.now().plusHours(12));
        trip.setId(2L);
        Reservation reservation = new Reservation(client, "1234567890", 2L, trip);
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
