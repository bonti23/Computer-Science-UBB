package ro.mpp2024;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import ro.mpp2024.controller.LoginView;
import ro.mpp2024.repository.*;
import ro.mpp2024.service.Service;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class FXMain extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/loginview.fxml"));
        AnchorPane layout = fxmlLoader.load();
        stage.setScene(new Scene(layout));
        Service service = getService();
        LoginView loginController = fxmlLoader.getController();
        loginController.setService(service);
        stage.show();
    }

    static Service getService() {
        Properties props = new Properties();
        try {
            props.load(new FileReader("bd.config"));
        } catch (IOException e) {
            System.out.println("cannot find bd.config " + e);
        }

        JdbcUtils jdbcUtils = new JdbcUtils(props);
        RepositoryUser userRepository = new RepositoryDBUser(jdbcUtils);
        RepositoryGame gameRepository = new RepositoryDBGame(jdbcUtils);
        RepositoryPurchase purchaseRepository = new RepositoryDBPurchase(jdbcUtils);

        Service service = new Service(gameRepository, userRepository, purchaseRepository);
        return service;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
