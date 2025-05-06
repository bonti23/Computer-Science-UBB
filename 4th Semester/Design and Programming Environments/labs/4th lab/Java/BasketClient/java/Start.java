package ro.mpp2024;
import javafx.application.Application;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import ro.mpp2024.controller.LoginView;
import ro.mpp2024.jdbc.JdbcUtils;
import ro.mpp2024.jdbc.RepositoryDBGame;
import ro.mpp2024.jdbc.RepositoryDBPurchase;
import ro.mpp2024.jdbc.RepositoryDBUser;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class Start extends Application {
    private Stage primaryStage;

    private static int defaultPort = 55556;
    private static String defaultServer = "localhost";


    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        System.out.println("In start");

        Properties clientProps = new Properties();
        try {
            clientProps.load(Start.class.getResourceAsStream("/client.properties"));
            System.out.println("Client properties set. ");
            clientProps.list(System.out);
        } catch (IOException e) {
            System.err.println("Cannot find client.properties " + e);
            return;
        }

        String serverIP = clientProps.getProperty("server.host", defaultServer);
        int serverPort = defaultPort;
        try {
            serverPort = Integer.parseInt(clientProps.getProperty("server.port"));
        } catch (NumberFormatException ex) {
            System.err.println("Wrong port number " + ex.getMessage());
        }

        System.out.println("Using server IP " + serverIP);
        System.out.println("Using server port " + serverPort);

        IService server = new BasketServicesRpcProxy(serverIP, serverPort);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/loginview.fxml"));
        AnchorPane root = loader.load();
        LoginView loginController = loader.getController();
        loginController.setService(server);

        primaryStage.setScene(new Scene(root));
        primaryStage.setTitle("Basket Login");
        primaryStage.show();

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
