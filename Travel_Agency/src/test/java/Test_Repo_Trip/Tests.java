package Test_Repo_Trip;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.project.travel_agency.Domain.Trip;
import org.project.travel_agency.Repository.DB_Repository.Repo_Trip;
import org.project.travel_agency.Utility.DB_Utils;

import java.time.LocalDateTime;
import java.util.Optional;

public class Tests {
    static private final DB_Utils DB_connection = new DB_Utils("test_bd.config");
    static private final Repo_Trip repoTrip = new Repo_Trip(DB_connection);


        @Test
        @DisplayName("Add trip test")
        public void testAdd() {
            LocalDateTime tripDate = LocalDateTime.now();
            Trip trip = new Trip("Paris", "EuroLines", 200L, 30L, tripDate, tripDate.plusHours(8), tripDate.plusHours(10));

            Optional<Trip> addedTrip = repoTrip.add(trip);

            Assertions.assertTrue(addedTrip.isPresent(), "Trip should be added");

            Trip actualTrip = addedTrip.get();

            Assertions.assertEquals("Paris", actualTrip.getDestination(), "Trip destination should match");
            Assertions.assertEquals("EuroLines", actualTrip.getTransportCompany(), "Transport company should match");
            Assertions.assertEquals(200L, actualTrip.getPrice(), "Trip price should match");
            Assertions.assertEquals(30L, actualTrip.getAvailableSeats(), "Available seats should match");
            Assertions.assertEquals(tripDate, actualTrip.getDate(), "Trip date should match");
            Assertions.assertEquals(tripDate.plusHours(8), actualTrip.getStartHour(), "Trip start hour should match");
            Assertions.assertEquals(tripDate.plusHours(10), actualTrip.getFinishHour(), "Trip finish hour should match");

            repoTrip.delete(actualTrip);
        }

        @Test
        @DisplayName("Delete trip test")
        public void testDelete() {
            LocalDateTime tripDate = LocalDateTime.now();
            Trip trip = new Trip("Paris", "EuroLines", 200L, 30L, tripDate, tripDate.plusHours(8), tripDate.plusHours(10));
            trip = repoTrip.add(trip).orElse(null);

            Assertions.assertNotNull(trip, "Added trip should not be null");

            Optional<Trip> deletedTrip = repoTrip.delete(trip);

            Assertions.assertTrue(deletedTrip.isPresent(), "Trip should be deleted");

            Optional<Trip> foundTrip = repoTrip.find(trip.getId());
            Assertions.assertFalse(foundTrip.isPresent(), "Deleted trip should not be found");
        }

        @Test
        @DisplayName("Update trip test")
        public void testUpdate() {
            LocalDateTime tripDate = LocalDateTime.now();
            Trip trip = new Trip("Paris", "EuroLines", 200L, 30L, tripDate, tripDate.plusHours(8), tripDate.plusHours(10));
            trip = repoTrip.add(trip).orElse(null);

            Assertions.assertNotNull(trip, "Added trip should not be null");

            // Update trip details
            trip.setDestination("London");
            trip.setTransportCompany("GreatWestern");
            trip.setPrice(250L);
            trip.setAvailableSeats(20L);
            trip.setDate(tripDate.plusDays(1));
            trip.setStartHour(tripDate.plusDays(1).plusHours(9));
            trip.setFinishHour(tripDate.plusDays(1).plusHours(11));

            Optional<Trip> updatedTrip = repoTrip.update(trip);

            Assertions.assertTrue(updatedTrip.isPresent(), "Trip should be updated");

            Trip actualTrip = updatedTrip.get();
            Assertions.assertEquals("London", actualTrip.getDestination(), "Trip destination should be updated");
            Assertions.assertEquals("GreatWestern", actualTrip.getTransportCompany(), "Transport company should be updated");
            Assertions.assertEquals(250L, actualTrip.getPrice(), "Trip price should be updated");
            Assertions.assertEquals(20L, actualTrip.getAvailableSeats(), "Available seats should be updated");
            Assertions.assertEquals(tripDate.plusDays(1), actualTrip.getDate(), "Trip date should be updated");
            Assertions.assertEquals(tripDate.plusDays(1).plusHours(9), actualTrip.getStartHour(), "Trip start hour should be updated");
            Assertions.assertEquals(tripDate.plusDays(1).plusHours(11), actualTrip.getFinishHour(), "Trip finish hour should be updated");

            repoTrip.delete(actualTrip);
        }

        @Test
        @DisplayName("Find trip test")
        public void testFind() {
            LocalDateTime tripDate = LocalDateTime.now();
            Trip trip = new Trip("Paris", "EuroLines", 200L, 30L, tripDate, tripDate.plusHours(8), tripDate.plusHours(10));

            trip = repoTrip.add(trip).orElse(null);

            Assertions.assertNotNull(trip, "Added trip should not be null");

            Optional<Trip> foundTrip = repoTrip.find(trip.getId());

            Assertions.assertTrue(foundTrip.isPresent(), "Trip should be found");

            Trip actualTrip = foundTrip.get();
            Assertions.assertEquals("Paris", actualTrip.getDestination(), "Found trip destination should match");
            Assertions.assertEquals("EuroLines", actualTrip.getTransportCompany(), "Found transport company should match");
            Assertions.assertEquals(200L, actualTrip.getPrice(), "Found trip price should match");
            Assertions.assertEquals(30L, actualTrip.getAvailableSeats(), "Found available seats should match");
            //Assertions.assertEquals(tripDate, actualTrip.getDate(), "Found trip date should match");
           // Assertions.assertEquals(tripDate.plusHours(8), actualTrip.getStartHour(), "Found trip start hour should match");
            //Assertions.assertEquals(tripDate.plusHours(10), actualTrip.getFinishHour(), "Found trip finish hour should match");

            repoTrip.delete(actualTrip);
        }
    }


