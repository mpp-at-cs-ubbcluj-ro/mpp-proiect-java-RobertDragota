package org.project.travel_agency.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.project.travel_agency.Application.Login;
import org.project.travel_agency.Domain.Account;
import org.project.travel_agency.Domain.Trip;
import org.project.travel_agency.Service.ServiceController;

import java.time.LocalDateTime;

public class App_Controller {
    @FXML
    private TextField destination;
    @FXML
    private TableView<Trip> FlightTable;
    @FXML
    private TableColumn<Trip, Long> FlightId_column;
    @FXML
    private TableColumn<Trip, String> Destination_column;
    @FXML
    private TableColumn<Trip, String> Company_column;
    @FXML
    private TableColumn<Trip, LocalDateTime> Departure_column;
    @FXML
    private TableColumn<Trip, LocalDateTime> Arrival_column;
    @FXML
    private TableColumn<Trip, Long> Price_column;
    @FXML
    private TableColumn<Trip, Long> Seats_column;
    @FXML
    private Button booking_button;
    @FXML
    private Button search_button;
    @FXML
    private TextField fromHour;
    @FXML
    private TextField toHour;
    @FXML
    private Button logout_button;

    private Account account;
    private Stage parentStage;
    private Stage LoginStage;
    private ServiceController serviceController;

    public void setAccount(Account account) {
        this.account = account;
    }

    public void setParentStage(Stage parentStage) {
        this.parentStage = parentStage;
    }

    public void setLoginStage(Stage loginStage) {
        LoginStage = loginStage;
    }
    private void initializeTable() {
        FlightId_column.setCellValueFactory(new PropertyValueFactory<>("id"));
        Destination_column.setCellValueFactory(new PropertyValueFactory<>("destination"));
        Company_column.setCellValueFactory(new PropertyValueFactory<>("transportCompany"));
        Departure_column.setCellValueFactory(new PropertyValueFactory<>("startHour"));
        Arrival_column.setCellValueFactory(new PropertyValueFactory<>("finishHour"));
        Price_column.setCellValueFactory(new PropertyValueFactory<>("price"));
        Seats_column.setCellValueFactory(new PropertyValueFactory<>("availableSeats"));
    }

    private void populateTable() {
        FlightTable.getItems().clear();
        Iterable<Trip> trips = serviceController.getTripService().getAll();
        for (Trip trip : trips) {
            FlightTable.getItems().add(trip);
        }
    }
    public void updateTable() {
        populateTable();
    }

    public void setService(ServiceController serviceController) {
        this.serviceController = serviceController;
        initializeTable();
        populateTable();
        setupDoubleClickEvent();
    }

    private void setupDoubleClickEvent() {
        FlightTable.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2 && !FlightTable.getSelectionModel().isEmpty()) {
                Trip selectedTrip = FlightTable.getSelectionModel().getSelectedItem();
                openNewWindow(selectedTrip);

            }
        });
    }

    private void openNewWindow(Trip trip) {
        try {
            FXMLLoader loader = new FXMLLoader(Login.class.getResource("/org/project/travel_agency/Reservation.fxml"));
            Parent root = loader.load();
            Reservation_Controller reservationController = loader.getController();
            reservationController.setService(serviceController);
            reservationController.setTrip(trip);
            reservationController.setAccount(account);
            reservationController.setAppController(this);
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Booking");
            stage.setResizable(false);
            stage.setScene(scene);
            reservationController.setParentStage(stage);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void handleSearch(ActionEvent actionEvent) {
        FlightTable.getItems().clear();
        if (destination.getText().isEmpty() || fromHour.getText().isEmpty() || toHour.getText().isEmpty()) {
            populateTable();
            return;
        }
        String dest = destination.getText();
        int from = Integer.parseInt(fromHour.getText());
        int to = Integer.parseInt(toHour.getText());

        if (from > to || from < 0 || from > 24 || to > 24) {
            System.out.println("Invalid hours");
            return;
        }
        {
            Iterable<Trip> trips = serviceController.getTripService().filterTrips(dest, from, to);
            for (Trip trip : trips) {
                FlightTable.getItems().add(trip);
            }
        }
        destination.clear();
        fromHour.clear();
        toHour.clear();
    }

    public void handleLogout(ActionEvent actionEvent) {
        parentStage.close();
        LoginStage.show();
    }
}
