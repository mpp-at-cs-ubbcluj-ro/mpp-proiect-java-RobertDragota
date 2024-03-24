package org.project.travel_agency.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.project.travel_agency.Application.Login;
import org.project.travel_agency.Service.ServiceController;

import java.io.IOException;

public class Login_Controller {
    @FXML
    private PasswordField UserPassword;
    @FXML
    private TextField UserName;
    @FXML
    private Button login_button;

    @FXML
    private Button register_button;

    private ServiceController serviceController;

    private Stage parentStage;

    public void setParentStage(Stage parentStage) {
        this.parentStage = parentStage;
    }

    public void handleLogin(ActionEvent actionEvent) throws IOException {
        String username = UserName.getText();
        String password = UserPassword.getText();
        var account_srv = serviceController.getAccountService();
        var account = account_srv.findByUsername(username);
        if (account.isPresent() && account.get().getPassword().equals(password)) {

            FXMLLoader loader = new FXMLLoader(Login.class.getResource("/org/project/travel_agency/App.fxml"));
            Parent root = loader.load();
            App_Controller appController = loader.getController();
            appController.setService(serviceController);
            appController.setAccount(account.get());
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("App");
            stage.setResizable(false);
            stage.setScene(scene);
            appController.setParentStage(stage);
            appController.setLoginStage(parentStage);
            UserName.clear();
            UserPassword.clear();
            stage.show();
            UserName.clear();
            UserPassword.clear();
            parentStage.close();
        } else {
            System.out.println("Invalid username or password");
        }
    }

    public void handleRegister(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(Login.class.getResource("/org/project/travel_agency/Register.fxml"));
        Parent root = loader.load();
        Register_Controller registerController = loader.getController();
        registerController.setService(serviceController);
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setTitle("App");
        stage.setResizable(false);
        stage.setScene(scene);
        registerController.setParentStage(stage);
        UserName.clear();
        UserPassword.clear();
        stage.show();
    }

    public void setService(ServiceController serviceController) {
        this.serviceController = serviceController;
    }
}
