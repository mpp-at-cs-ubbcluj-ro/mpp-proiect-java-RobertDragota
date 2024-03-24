package org.project.travel_agency.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.project.travel_agency.Domain.Account;
import org.project.travel_agency.Domain.Trip;
import org.project.travel_agency.Service.ServiceController;

public class Reservation_Controller {

    @FXML
    private TextField ClientName;
    @FXML
    private TextField ClientPhone;
    @FXML
    private TextField NoTickets;
    @FXML
    private Button booking_button;
    private Account account;

    private Stage parentStage;

    private App_Controller app_controller;

    private Trip trip;
    private ServiceController serviceController;

    public void handleBooking(ActionEvent actionEvent) {
        String name = ClientName.getText();
        String phone = ClientPhone.getText();
        Long tickets = Long.parseLong(NoTickets.getText());
        var reservation_srv = serviceController.getReservationService();
        var reservation= reservation_srv.createReservation(account, name, phone, tickets, trip);
        reservation_srv.add(reservation);
        var trip_srv = serviceController.getTripService();
        trip.setAvailableSeats(trip.getAvailableSeats() - tickets);
        trip_srv.update(trip);
        app_controller.updateTable();
        parentStage.close();
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public void setTrip(Trip trip) {
        this.trip = trip;
    }

    public void setParentStage(Stage parentStage) {
        this.parentStage = parentStage;
    }
    public void setService(ServiceController serviceController) {
        this.serviceController = serviceController;

    }
    public void setAppController(App_Controller app_controller) {
        this.app_controller = app_controller;
    }
}
