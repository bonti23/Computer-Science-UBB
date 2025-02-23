package ubb.scs.map.examen;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import ubb.scs.map.examen.controller.MainController;
import ubb.scs.map.examen.domain.validation.CityValidation;
import ubb.scs.map.examen.domain.validation.TrainStationValidation;
import ubb.scs.map.examen.repository.CityDBRepository;
import ubb.scs.map.examen.repository.TrainStationDBRepository;
import ubb.scs.map.examen.service.Service;

import java.io.IOException;

public class HelloApplication extends Application {
    Service service;

    @Override
    public void start(Stage primaryStage) throws IOException {
        try {
            System.out.println("Starting the application...");
            this.service = new Service(
                    new TrainStationDBRepository("jdbc:postgresql://localhost:5432/trenuri", "alexandrabontidean", "alexandramiha", new TrainStationValidation()),
                    new CityDBRepository("jdbc:postgresql://localhost:5432/trenuri", "alexandrabontidean", "alexandramiha", new CityValidation())
            );

            primaryStage.setTitle("trenuri examen");
            startView(primaryStage);
            primaryStage.show();
        }catch(Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error starting the application", e);
        }
    }
    private void startView(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/ubb/scs/map/examen/main-view.fxml"));
        AnchorPane Layout = fxmlLoader.load();
        stage.setScene(new Scene(Layout));
        stage.setTitle("WELCOME!");

        MainController startController = fxmlLoader.getController();
        startController.setService(service);
    }
    public static void main(String[] args) {
        launch(args);
    }
}
