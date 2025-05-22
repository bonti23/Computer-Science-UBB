package ro.mpp2024;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ro.mpp2024.controller.LoginView;

public class Start extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        String serverIP = "localhost";
        int serverPort = 55556;
        IService service = new GrpcService(serverIP, serverPort);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/loginview.fxml"));
        Parent root = loader.load();
        LoginView loginController = loader.getController();
        loginController.setService(service);

        primaryStage.setScene(new Scene(root));
        primaryStage.setTitle("Basket Login");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
