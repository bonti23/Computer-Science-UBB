package bonti;
import bonti.controller.LoginView;
import javafx.application.Application;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

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
    public static void main(String[] args) {
        launch(args);
    }
}