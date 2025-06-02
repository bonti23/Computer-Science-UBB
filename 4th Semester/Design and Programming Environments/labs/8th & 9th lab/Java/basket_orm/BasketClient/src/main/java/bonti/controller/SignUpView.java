package bonti.controller;

import bonti.IService;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class SignUpView {
    private IService service;

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

    public void setService(IService service) {
        this.service = service;
    }

    @FXML
    void initialize() {
        signUpButton.setOnAction(event -> handleSignUp());

        signUpButton.setDisable(true);

        nameField.textProperty().addListener((observable, oldValue, newValue) -> updateSignUpButtonState());
        usernameField.textProperty().addListener((observable, oldValue, newValue) -> updateSignUpButtonState());
        passwordField.textProperty().addListener((observable, oldValue, newValue) -> updateSignUpButtonState());
    }

    private void updateSignUpButtonState() {
        boolean isValid = !nameField.getText().isBlank() &&
                !usernameField.getText().isBlank() &&
                !passwordField.getText().isBlank();
        signUpButton.setDisable(!isValid);
        errorMessage.setText("");
    }

    private void handleSignUp() {
        String name = nameField.getText();
        String username = usernameField.getText();
        String password = passwordField.getText();

        // Debugging
        System.out.println("Name: " + name);
        System.out.println("Username: " + username);
        System.out.println("Password: " + password);

        if (name == null || name.isBlank() ||
                username == null || username.isBlank() ||
                password == null || password.isBlank()) {
            errorMessage.setText("All fields are required.");
            return;
        }

        try {
            service.signup(name, username, password);
            showSuccessAlert("Account created successfully!");
        } catch (IllegalArgumentException e) {
            if (e.getMessage().equals("Username already exists!")) {
                errorMessage.setText("The username is already taken. Please choose a different one.");
            } else {
                errorMessage.setText(e.getMessage());
            }
        } catch (Exception e) {
            errorMessage.setText("An unexpected error occurred: " + e.getMessage());
        }
    }




    private void showSuccessAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
        ((Stage) signUpButton.getScene().getWindow()).close();
    }
}