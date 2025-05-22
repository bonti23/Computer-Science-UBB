package bonti.controller;
import bonti.DTO.UserDTO;
import bonti.domain.Personnel;
import bonti.domain.Role;
import bonti.service.IService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import lombok.Setter;

import java.io.IOException;

public class LoginView {
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Label feedbackLabel;

    @Setter
    private IService service;

    public void show(Stage stage, IService service) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/loginview.fxml"));
            Parent root = loader.load();

            LoginView loginController = loader.getController();
            loginController.setService(service);

            stage.setScene(new Scene(root));
            stage.setTitle("Login");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    @FXML
    private void handleLogin() {
        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();

        if (username.isEmpty() || password.isEmpty()) {
            feedbackLabel.setText("Please enter both username and password.");
            return;
        }

        UserDTO user = service.login(username, password);

        if (user != null) {
            feedbackLabel.setText("Login successful!");

            if (user != null) {
                feedbackLabel.setText("Login successful!");

                if (user.getRole() == Role.PERSONNEL) {
                    Personnel personnel = service.getPersonnelByUsername(user.getUsername());

                    if (personnel != null) {
                        Stage personnelStage = new Stage();
                        PersonnelView personnelView = new PersonnelView(service, personnel);
                        personnelView.show(personnelStage);
                        feedbackLabel.getScene().getWindow().hide();
                    } else {
                        feedbackLabel.setText("Personnel not found.");
                    }
                } else if (user.getRole() == Role.CHEMIST) {
                    Stage chemistStage = new Stage();
                    ChemistView chemistView = new ChemistView(service);
                    chemistView.show(chemistStage);
                    feedbackLabel.getScene().getWindow().hide();
                }

            } else {
                feedbackLabel.setText("Invalid credentials.");
            }

        } else {
            feedbackLabel.setText("Invalid credentials.");
        }
    }
}
