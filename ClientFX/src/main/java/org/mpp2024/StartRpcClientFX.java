package org.mpp2024;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.mpp2024.Controllers.Login_Controller;
import org.mpp2024.RcpProtocol.AppServicesRpcProxy;

import java.io.IOException;
import java.util.Properties;


public class StartRpcClientFX extends Application {
    private Stage primaryStage;

    private static int defaultChatPort = 55555;
    private static String defaultServer = "localhost";


    public void start(Stage primaryStage) throws Exception {
        System.out.println("In start");
        Properties clientProps = new Properties();
        try {
            clientProps.load(StartRpcClientFX.class.getResourceAsStream("/client.properties"));
            System.out.println("Client properties set. ");
            clientProps.list(System.out);
        } catch (IOException e) {
            System.err.println("Cannot find chatclient.properties " + e);
            return;
        }
        String serverIP = clientProps.getProperty("app.server.host", defaultServer);
        int serverPort = defaultChatPort;

        try {
            serverPort = Integer.parseInt(clientProps.getProperty("app.server.port"));
        } catch (NumberFormatException ex) {
            System.err.println("Wrong port number " + ex.getMessage());
            System.out.println("Using default port: " + defaultChatPort);
        }
        System.out.println("Using server IP " + serverIP);
        System.out.println("Using server port " + serverPort);

        ServiceAppInterface server = new AppServicesRpcProxy(serverIP, serverPort);


        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("Login.fxml"));
        Parent root = loader.load();


        Login_Controller controller = loader.getController();
        controller.setService(server);

        controller.setParentStage(primaryStage);

        primaryStage.setTitle("Booking App");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();


    }


}


