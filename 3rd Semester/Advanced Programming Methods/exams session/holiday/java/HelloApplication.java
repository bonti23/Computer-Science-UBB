package ubb.scs.map.vacante;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import ubb.scs.map.vacante.controller.ClientController;
import ubb.scs.map.vacante.controller.MainController;
import ubb.scs.map.vacante.domain.Client;
import ubb.scs.map.vacante.domain.validation.*;
import ubb.scs.map.vacante.repository.*;
import ubb.scs.map.vacante.service.Service;

import java.io.IOException;
import java.util.List;

public class HelloApplication extends Application {
    Service service;

    @Override
    public void start(Stage primaryStage) throws IOException {
        try {
            System.out.println("Starting the application...");

            List<Long> clientIds = List.of(1L, 2L, 6L);  // Aici poți să setezi ID-ul clientului pe care vrei să-l folosești

            // Asigură-te că 'service' este instanțiat înainte de utilizare
            this.service = new Service(
                    new LocationDBRepository(
                            "jdbc:postgresql://localhost:5432/vacante",
                            "alexandrabontidean",
                            "alexandramiha",
                            new LocationValidation()
                    ),
                    new HotelDBRepository(
                            "jdbc:postgresql://localhost:5432/vacante",
                            "alexandrabontidean",
                            "alexandramiha",
                            new HotelValidation()
                    ),
                    new SpecialOfferDBRepository(
                            "jdbc:postgresql://localhost:5432/vacante",
                            "alexandrabontidean",
                            "alexandramiha",
                            new SpecialOfferValidation()
                    ),
                    new ClientDBRepository(
                            "jdbc:postgresql://localhost:5432/vacante",
                            "alexandrabontidean",
                            "alexandramiha",
                            new ClientValidation()
                    ),
                    new ReservationDBRepository(
                            "jdbc:postgresql://localhost:5432/vacante",
                            "alexandrabontidean",
                            "alexandramiha",
                            new ReservationValidation()
                    )
            );


            //Deschidem fereasta principala:
            openMainView();
            // Deschidem fereastra clientului
            for (Long clientId : clientIds) {
                Client client = service.getClientById(clientId);
                if (client != null) {
                    Stage clientStage = new Stage();
                    clientStage.setTitle(client.getName());
                    openClientView(clientStage, client);
                    clientStage.show();
                } else {
                    System.out.println("Client not found with ID: " + clientId);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error starting the application", e);
        }
    }

    private void openMainView() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/ubb/scs/map/vacante/start-view.fxml"));
        AnchorPane layout = fxmlLoader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(layout));

        // Setăm controller-ul pentru MainView
        MainController mainController = fxmlLoader.getController();
        mainController.setService(service);
        stage.setTitle("Main Screen");
        stage.show();
    }

    private void openClientView(Stage stage, Client client) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/ubb/scs/map/vacante/client-view.fxml"));
        AnchorPane layout = fxmlLoader.load();
        stage.setScene(new Scene(layout));

        ClientController clientController = fxmlLoader.getController();
        clientController.setService(service, client.getID());  // Setăm service și id-ul clientului
    }

    public static void main(String[] args) {
        launch(args);
    }
}
