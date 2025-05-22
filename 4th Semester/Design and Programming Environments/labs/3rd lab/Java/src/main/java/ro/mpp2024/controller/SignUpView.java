package ro.mpp2024.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import ro.mpp2024.service.Service;

public class SignUpView {
    private Service service;

    @FXML
    private TextField nameField;

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label errorMessage;

    @FXML
    private Button signUpButton;

    public void setService(Service service) {
        this.service = service;
    }

    @FXML
    void initialize() {
        signUpButton.setOnAction(event -> handleSignUp());
    }

    private void handleSignUp() {
        String name = nameField.getText();
        String username = usernameField.getText();
        String password = passwordField.getText();
        try {
            if (name.isEmpty() || username.isEmpty() || password.isEmpty()) {
                throw new IllegalArgumentException("all fields are required.");
            }
            service.signup(name, username, password);
            showSuccessAlert("account created successfully!");

        } catch (IllegalArgumentException e) {
            errorMessage.setText(e.getMessage());
        }
    }

    private void showSuccessAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("success");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
        ((Stage) signUpButton.getScene().getWindow()).close();
    }
}
