package org.project.travel_agency.Application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.project.travel_agency.Controllers.Login_Controller;
import org.project.travel_agency.Repository.DB_Repository.Repo_Account;
import org.project.travel_agency.Repository.DB_Repository.Repo_Reservation;
import org.project.travel_agency.Repository.DB_Repository.Repo_Trip;
import org.project.travel_agency.Service.ServiceController;
import org.project.travel_agency.Service.Service_Account;
import org.project.travel_agency.Service.Service_Reservation;
import org.project.travel_agency.Service.Service_Trip;
import org.project.travel_agency.Utility.DB_Utils;
import org.project.travel_agency.Utility.Validation.Validator_Account;
import org.project.travel_agency.Utility.Validation.Validator_Reservation;
import org.project.travel_agency.Utility.Validation.Validator_Trip;

import java.io.IOException;

public class Login extends Application {

    private ServiceController serviceController;

    @Override
    public void start(Stage stage) throws Exception {

        DB_Utils db_utils = new DB_Utils("bd.config");
        Repo_Account repo_account = new Repo_Account(db_utils);
        Repo_Reservation repo_reservation = new Repo_Reservation(db_utils);
        Repo_Trip repo_trip = new Repo_Trip(db_utils);
        Validator_Account validator_account = new Validator_Account();
        Validator_Trip validator_trip = new Validator_Trip();
        Validator_Reservation validator_reservation = new Validator_Reservation();
        Service_Account service_account = new Service_Account(repo_account, validator_account);
        Service_Reservation service_reservation = new Service_Reservation(repo_reservation, validator_reservation);
        Service_Trip service_trip = new Service_Trip(repo_trip, validator_trip);
        serviceController = new ServiceController(service_account, service_reservation, service_trip);
        stage.setResizable(false);
        initView(stage);
        stage.show();

    }

    private void initView(Stage primaryStage) throws IOException {
        FXMLLoader loginLoader = new FXMLLoader();
        loginLoader.setLocation(getClass().getResource("/org/project/travel_agency/Login.fxml"));
        Parent root = loginLoader.load();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        Login_Controller loginController = loginLoader.getController();
        loginController.setService(serviceController);
        loginController.setParentStage(primaryStage);

    }

    public static void main(String[] args) {
        launch();
    }
}
