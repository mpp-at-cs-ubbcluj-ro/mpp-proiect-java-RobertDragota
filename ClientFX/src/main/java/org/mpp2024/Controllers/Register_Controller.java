package org.mpp2024.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.mpp2024.AppException;
import org.mpp2024.ServiceAppInterface;
import org.mpp2024.Utility.Validation.ValidException;


public class Register_Controller {
    @FXML
    private Button register_button;
    @FXML
    private TextField UserPassword;
    @FXML
    private TextField UserName;

    private ServiceAppInterface serviceController;
    private Stage parentStage;

    public void setParentStage(Stage parentStage) {
        this.parentStage = parentStage;
    }

    public void handleRegister(ActionEvent actionEvent) {
        String username = UserName.getText();
        String password = UserPassword.getText();
        try {
            serviceController.MakeAccount(username, password);
        } catch (ValidException | AppException e) {
            e.printStackTrace();
        }

        UserName.clear();
        UserPassword.clear();
        parentStage.close();
    }

    public void setService(ServiceAppInterface serviceController) {
        this.serviceController = serviceController;
    }
}
