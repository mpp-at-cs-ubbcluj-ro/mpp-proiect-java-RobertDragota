package org.mpp2024.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.mpp2024.Account;
import org.mpp2024.AppException;
import org.mpp2024.ServiceAppInterface;
import org.mpp2024.Trip;


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
    private ServiceAppInterface serviceController;

    public void handleBooking(ActionEvent actionEvent) throws AppException {
        String name = ClientName.getText();
        String phone = ClientPhone.getText();
        Long tickets = Long.parseLong(NoTickets.getText());
        serviceController.MakeReservation(account, name, phone, tickets, trip);
        //app_controller.updateTrips(serviceController.Get_All_Trips());
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
    public void setService(ServiceAppInterface serviceController) {
        this.serviceController = serviceController;

    }
    public void setAppController(App_Controller app_controller) {
        this.app_controller = app_controller;
    }
}
