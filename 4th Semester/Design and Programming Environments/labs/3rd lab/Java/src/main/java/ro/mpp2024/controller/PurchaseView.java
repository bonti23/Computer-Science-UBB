package ro.mpp2024.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import ro.mpp2024.domain.Game;
import ro.mpp2024.domain.Purchase;
import ro.mpp2024.service.Service;

public class PurchaseView {
    private Service service;
    private Game selectedGame;

    @FXML
    private Label gameDetailsLabel;

    @FXML
    private TextField clientNameField;

    @FXML
    private TextField addressField;

    @FXML
    private TextField seatsField;

    @FXML
    private Button cancelButton;

    @FXML
    private Button purchaseButton;

    @FXML
    private Label errorMessage;

    public void setService(Service service) {
        this.service = service;
    }

    public void setGame(Game game) {
        this.selectedGame = game;
        updateGameDetails();
    }
    @FXML
    void initialize() {
        purchaseButton.setOnAction(event -> handlePurchase());
        cancelButton.setOnAction(event -> closeWindow());
    }
    private void updateGameDetails() {
        gameDetailsLabel.setText(selectedGame.getTeamA() + " vs " + selectedGame.getTeamB());
    }
    private void handlePurchase() {
        String clientName = clientNameField.getText();
        String address = addressField.getText();
        String seatsText = seatsField.getText();
        if (clientName.isEmpty() || address.isEmpty() || seatsText.isEmpty()) {
            errorMessage.setText("all fields are required!");
            return;
        }

        try {
            int seats = Integer.parseInt(seatsText);

            if (seats <= 0) {
                errorMessage.setText("invalid number of seats!");
                return;
            }

            if (seats > selectedGame.getSeats()) {
                errorMessage.setText("not enough seats available!");
                return;
            }

            Purchase purchase = new Purchase(clientName, selectedGame.get_identitykey(), seats, address);
            service.add_purchase(purchase);

            showSuccessAlert("purchase added successfully!");
            closeWindow();

        } catch (NumberFormatException e) {
            errorMessage.setText("number of seats must be a valid number!");
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
    }

    private void closeWindow() {
        Stage stage = (Stage) purchaseButton.getScene().getWindow();
        stage.close();
    }
}
