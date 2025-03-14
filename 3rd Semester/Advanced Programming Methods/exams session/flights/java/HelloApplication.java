package ubb.scs.map.zboruri;

import ubb.scs.map.zboruri.controller.LoginController;
import ubb.scs.map.zboruri.domain.validation.ClientValidation;
import ubb.scs.map.zboruri.domain.validation.FlightValidation;
import ubb.scs.map.zboruri.domain.validation.TicketValidation;
import ubb.scs.map.zboruri.repository.ClientDBRepository;
import ubb.scs.map.zboruri.repository.FlightDBRepository;
import ubb.scs.map.zboruri.repository.TicketDBRepository;
import ubb.scs.map.zboruri.service.Service;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    Service service;
    @Override
    public void start(Stage primaryStage) throws IOException {
        try {
            System.out.println("Starting the application...");
            this.service = new Service(
                    new ClientDBRepository(
                            "jdbc:postgresql://localhost:5432/zboruri",
                            "alexandrabontidean",
                            "alexandramiha",
                            new ClientValidation()
                    ),
                    new FlightDBRepository(
                            "jdbc:postgresql://localhost:5432/zboruri",
                            "alexandrabontidean",
                            "alexandramiha",
                            new FlightValidation()
                    ),
                    new TicketDBRepository(
                            "jdbc:postgresql://localhost:5432/zboruri",
                            "alexandrabontidean",
                            "alexandramiha",
                            new TicketValidation()
                    )
            );

            primaryStage.setTitle("Main");
            startView(primaryStage);
            primaryStage.show();
        }catch(Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error starting the application", e);
        }
    }
    private void startView(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/ubb/scs/map/zboruri/login-view.fxml"));
        AnchorPane Layout = fxmlLoader.load();
        stage.setScene(new Scene(Layout));

        LoginController startController = fxmlLoader.getController();
        startController.setService(service);
    }
    public static void main(String[] args) {
        launch(args);
    }
}
