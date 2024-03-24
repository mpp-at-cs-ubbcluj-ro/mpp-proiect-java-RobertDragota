package org.project.travel_agency.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.project.travel_agency.Service.ServiceController;

public class Register_Controller {
    @FXML
    private Button register_button;
    @FXML
    private TextField UserPassword;
    @FXML
    private TextField UserName;

    private ServiceController serviceController;
    private Stage parentStage;

    public void setParentStage(Stage parentStage) {
        this.parentStage = parentStage;
    }

    public void handleRegister(ActionEvent actionEvent) {
        String username = UserName.getText();
        String password = UserPassword.getText();
        var account_srv = serviceController.getAccountService();
        var account = account_srv.createAccount(username, password);
        account_srv.add(account);
        UserName.clear();
        UserPassword.clear();
        parentStage.close();
    }

    public void setService(ServiceController serviceController) {
        this.serviceController = serviceController;
    }
}
