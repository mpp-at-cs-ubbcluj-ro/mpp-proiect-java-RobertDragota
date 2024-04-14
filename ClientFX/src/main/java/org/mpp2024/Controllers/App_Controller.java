package org.mpp2024.Controllers;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.mpp2024.*;


import java.net.URL;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.StreamSupport;

public class App_Controller implements Initializable, AppObserverInterface {
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
    private ServiceAppInterface serviceController;
    ObservableList<Trip> modelTrip = FXCollections.observableArrayList();

    public void setAccount(Account account) {
        this.account = account;
    }

    public void setParentStage(Stage parentStage) {
        this.parentStage = parentStage;
    }

    public void setLoginStage(Stage loginStage) {
        LoginStage = loginStage;
    }

    @FXML
    public void initialize() {

        FlightId_column.setCellValueFactory(new PropertyValueFactory<>("id"));
        Destination_column.setCellValueFactory(new PropertyValueFactory<>("destination"));
        Company_column.setCellValueFactory(new PropertyValueFactory<>("transportCompany"));
        Departure_column.setCellValueFactory(new PropertyValueFactory<>("startHour"));
        Arrival_column.setCellValueFactory(new PropertyValueFactory<>("finishHour"));
        Price_column.setCellValueFactory(new PropertyValueFactory<>("price"));
        Seats_column.setCellValueFactory(new PropertyValueFactory<>("availableSeats"));

        FlightTable.setItems(modelTrip);
    }

    private void initializeTable() {
        List<Trip> tripList = null;
        try {
            tripList = StreamSupport.stream(serviceController.Get_All_Trips().spliterator(), false).toList();
        } catch (AppException e) {
            System.out.println("Error getting all curse: " + e.getMessage());
        }

        List<Trip> finalCurseList = tripList;
        Platform.runLater(() -> {
            modelTrip.setAll(finalCurseList);
        });

    }

    private void populateTable() throws AppException {
        FlightTable.getItems().clear();
        Iterable<Trip> trips = serviceController.Get_All_Trips();
        for (Trip trip : trips) {
            FlightTable.getItems().add(trip);
        }
    }



    public void setService(ServiceAppInterface serviceController) throws AppException {
        this.serviceController = serviceController;
        initialize();
        initializeTable();
        //populateTable();
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
            FXMLLoader loader = new FXMLLoader(StartRpcClientFX.class.getResource("/Reservation.fxml"));
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


    public void handleSearch(ActionEvent actionEvent) throws AppException {

        if (destination.getText().isEmpty() || fromHour.getText().isEmpty() || toHour.getText().isEmpty()) {
            return;
        }
        FlightTable.getItems().clear();
        String dest = destination.getText();
        int from = Integer.parseInt(fromHour.getText());
        int to = Integer.parseInt(toHour.getText());

        if (from > to || from < 0 || from > 24 || to > 24) {
            System.out.println("Invalid hours");
            return;
        }
        {
            Iterable<Trip> trips = serviceController.Get_All_Trip_By_Destination_From_To(dest, from, to);
            if(trips!=null) {
                modelTrip.clear();
                for (Trip trip : trips) {
                    modelTrip.add(trip);
                }
                Platform.runLater(() -> {
                    FlightTable.setItems(modelTrip);
                });
            }
        }

        destination.clear();
        fromHour.clear();
        toHour.clear();
    }

    public void handleLogout(ActionEvent actionEvent) {
        try {
            serviceController.Logout(account, this);
            parentStage.close();
            LoginStage.show();

        } catch (AppException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @Override
    public void updateTrips(Iterable<Trip> list) throws AppException {
        Platform.runLater(() -> {
            List<Trip> curseList = StreamSupport.stream(list.spliterator(), false).toList();
            modelTrip.setAll(curseList);
        });

    }
}
