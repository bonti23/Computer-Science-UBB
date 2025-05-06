package ro.mpp2024.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import ro.mpp2024.IService;
import ro.mpp2024.Service;
import ro.mpp2024.User;

import java.io.IOException;
import java.util.Optional;

public class LoginView {
    private IService service;
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    @FXML
    private Button login;
    @FXML
    private TextFlow signInFlow;
    @FXML
    private Label errorMessage;

    private Parent mainView;
    public void setService(IService service) {
        this.service = service;
    }
    @FXML
    void initialize(){
        Text signInText = new Text("sign up");
        signInText.setFill(javafx.scene.paint.Color.BLUE);
        signInText.setStyle("-fx-underline: true");
        signInText.setOnMouseClicked(event->openSignUpView());
        signInFlow.getChildren().add(signInText);
        login.setOnAction(event->handleLogin());
    }

    private void handleLogin(){
        String user = username.getText();
        String pass = password.getText();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/mainmenuview.fxml"));
            Parent root = loader.load();
            MainMenuView mainMenuController = loader.getController();
            mainMenuController.setService(service);

            User loggedInUser = service.login(user, pass, mainMenuController);
            if (loggedInUser != null) {
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.setTitle("main menu");
                stage.show();
                ((Stage) login.getScene().getWindow()).close();
            } else {
                errorMessage.setText("Invalid username or password.");
            }
        } catch (Exception e) {
            errorMessage.setText("Login failed: " + e.getMessage());
            e.printStackTrace();
        }
    }
    void openMainMenuView(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/mainmenuview.fxml"));
            Parent root = loader.load();
            MainMenuView mainMenuController = loader.getController();
            mainMenuController.setService(service);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("main menu");
            stage.show();
            ((Stage) login.getScene().getWindow()).close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    void openSignUpView(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/signupview.fxml"));
            Parent root = loader.load();
            SignUpView controller = loader.getController();
            controller.setService(service);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("sign up");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
