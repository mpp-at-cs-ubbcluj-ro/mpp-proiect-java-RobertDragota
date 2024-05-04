package org.mpp2024.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.mpp2024.Account;
import org.mpp2024.AppException;
import org.mpp2024.ServiceAppInterface;
import org.mpp2024.StartRpcClientFX;


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

    private ServiceAppInterface serviceController;


    private Stage parentStage;

    public void setParentStage(Stage parentStage) {
        this.parentStage = parentStage;
    }


    public void handleLogin(ActionEvent actionEvent) throws IOException {
        String username = UserName.getText();
        String password = UserPassword.getText();
        var account = new Account(username, password);
        try {
            FXMLLoader loader = new FXMLLoader(Login_Controller.class.getResource("/App.fxml"));
            Parent root = loader.load();
            App_Controller appController = loader.getController();
            serviceController.Login(account, appController);
            appController.setService(serviceController);
            appController.setAccount(account);
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
        } catch (AppException e) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Cannot login");
            alert.setContentText(e.getMessage());
            alert.showAndWait();

        }


    }

    public void handleRegister(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(StartRpcClientFX.class.getResource("resources/Register.fxml"));
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

    public void setService(ServiceAppInterface serviceController) {
        this.serviceController = serviceController;
    }
}
