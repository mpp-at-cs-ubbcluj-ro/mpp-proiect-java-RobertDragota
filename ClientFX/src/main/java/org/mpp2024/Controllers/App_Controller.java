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
import javafx.scene.control.*;
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
    private DatePicker Calendar;
    @FXML
    private ComboBox<String> ComboTo;
    @FXML
    private ComboBox<String> ComboFrom;
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
        ComboFrom.setItems(FXCollections.observableArrayList( "3:00", "3:30", "4:00", "4:30", "5:00", "5:30", "6:00", "6:30", "7:00", "7:30", "8:00", "8:30", "9:00", "9:30", "10:00", "10:30", "11:00", "11:30", "12:00", "12:30", "13:00", "13:30", "14:00", "14:30", "15:00", "15:30", "16:00", "16:30", "17:00", "17:30", "18:00", "18:30", "19:00", "19:30", "20:00", "20:30", "21:00", "21:30", "22:00", "22:30", "23:00", "23:30", "0:00", "0:30", "1:00", "1:30", "2:00", "2:30"));
        ComboTo.setItems(FXCollections.observableArrayList( "3:00", "3:30", "4:00", "4:30", "5:00", "5:30", "6:00", "6:30", "7:00", "7:30", "8:00", "8:30", "9:00", "9:30", "10:00", "10:30", "11:00", "11:30", "12:00", "12:30", "13:00", "13:30", "14:00", "14:30", "15:00", "15:30", "16:00", "16:30", "17:00", "17:30", "18:00", "18:30", "19:00", "19:30", "20:00", "20:30", "21:00", "21:30", "22:00", "22:30", "23:00", "23:30", "0:00", "0:30", "1:00", "1:30", "2:00", "2:30"));
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

        if (destination.getText().isEmpty() || ComboFrom.getValue().isEmpty() || ComboTo.getValue().isEmpty()) {
            return;
        }
        FlightTable.getItems().clear();
        String dest = destination.getText();
        String from = ComboFrom.getValue();
        String to = ComboTo.getValue();
        LocalDateTime date = Calendar.getValue().atTime(0, 0);
        LocalDateTime start = date.plusHours(Integer.parseInt(from.split(":")[0])).plusMinutes(Integer.parseInt(from.split(":")[1]));
        LocalDateTime finish = date.plusHours(Integer.parseInt(to.split(":")[0])).plusMinutes(Integer.parseInt(to.split(":")[1]));
        {
            Iterable<Trip> trips = serviceController.Get_All_Trip_By_Destination_From_To(dest, date,start, finish);
            if(trips!=null) {
                if(trips.iterator().hasNext()) {
                    modelTrip.clear();
                    for (Trip trip : trips) {
                        modelTrip.add(trip);
                    }
                    Platform.runLater(() -> {
                        FlightTable.setItems(modelTrip);
                    });
                }else {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Info");
                    alert.setHeaderText(null);
                    alert.setContentText("This destination has no flights available!");
                    alert.showAndWait();
                    initializeTable();
                }

            }
        }

        destination.clear();
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
