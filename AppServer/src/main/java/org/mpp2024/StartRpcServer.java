package org.mpp2024;

import org.mpp2024.Utility.DB_Utils;
import org.mpp2024.Utility.Validation.Validator_Account;
import org.mpp2024.Utility.Validation.Validator_Reservation;
import org.mpp2024.Utility.Validation.Validator_Trip;
import org.mpp2024.app_server.ServiceDispatcher;
import org.mpp2024.app_server.Service_Account;
import org.mpp2024.app_server.Service_Reservation;
import org.mpp2024.app_server.Service_Trip;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class StartRpcServer {
    private static int defaultPort=55555;
    public static void main(String[] args) {
        // UserRepository userRepo=new UserRepositoryMock();
        Properties serverProps=new Properties();
        try {
            serverProps.load(StartRpcServer.class.getResourceAsStream("/appserver.properties"));
            System.out.println("Server properties set. ");
            serverProps.list(System.out);

        } catch (IOException e) {
            System.err.println("Cannot find appserver.properties "+e);
            return;
        }

        DB_Utils dbUtils = new DB_Utils("bd.config");
        Repo_Account repoAccount=new Repo_Account(dbUtils);
        Validator_Account validatorAccount=new Validator_Account();
        Repo_Trip repoTrip=new Repo_Trip(dbUtils);
        Repo_Reservation repoReservation=new Repo_Reservation(dbUtils);
        ServiceAccountInferace serviceAccount=new Service_Account(repoAccount, validatorAccount);
        ServiceTripInterface serviceTrip=new Service_Trip(repoTrip, new Validator_Trip());
        ServiceReservationInterface serviceReservation=new Service_Reservation(repoReservation, new Validator_Reservation());
        ServiceAppInterface serviceController=new ServiceDispatcher(serviceAccount, serviceReservation,serviceTrip);
        int appServer=defaultPort;
        try {
            appServer = Integer.parseInt(serverProps.getProperty("app.server.port"));
        }catch (NumberFormatException nef){
            System.err.println("Wrong  Port Number"+nef.getMessage());
            System.err.println("Using default port "+defaultPort);
        }
        System.out.println("Starting server on port: "+appServer);
        AbstractServer server = new AppRpcConcurrentServer(appServer,serviceController);
        try {
            server.start();
        } catch (ServerException e) {
            System.err.println("Error starting the server" + e.getMessage());
        }finally {
            try {
                server.stop();
            }catch(ServerException e){
                System.err.println("Error stopping server "+e.getMessage());
            }
        }
    }
}
