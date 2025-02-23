package ubb.scs.map.examen.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import ubb.scs.map.examen.HelloApplication;
import ubb.scs.map.examen.service.Service;

public class MainController {
    private Service service;
    @FXML
    private Button loginButton;

    public void setService(Service service) {
        this.service = service;

    }
    public void handleLogin(ActionEvent actionEvent){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/ubb/scs/map/examen/client-view.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("routes");
            ClientController clientController = fxmlLoader.getController();
            clientController.setService(service);
            stage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
