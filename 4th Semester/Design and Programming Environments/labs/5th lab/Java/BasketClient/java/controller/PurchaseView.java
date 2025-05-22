package ro.mpp2024.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import ro.mpp2024.*;

public class PurchaseView implements IObserver {
    private IService service;
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

    public void setService(IService service) {
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
        // Update the UI with current game details
        gameDetailsLabel.setText(selectedGame.getTeamA() + " vs " + selectedGame.getTeamB());
        System.out.println("Selected Game ID: " + selectedGame.get_identitykey());
        // You may want to add a label to show the number of available seats
        // availableSeatsLabel.setText("Available seats: " + selectedGame.getSeats());
    }

    private void handlePurchase() {
        String clientName = clientNameField.getText();
        String address = addressField.getText();
        String seatsText = seatsField.getText();

        if (clientName.isEmpty() || address.isEmpty() || seatsText.isEmpty()) {
            errorMessage.setText("All fields are required!");
            return;
        }

        try {
            int seats = Integer.parseInt(seatsText);

            if (seats <= 0) {
                errorMessage.setText("Invalid number of seats!");
                return;
            }

            if (seats > selectedGame.getSeats()) {
                errorMessage.setText("Not enough seats available!");
                return;
            }

            long nextId = service.findAllPurchases().size() + 1;
            Purchase purchase = new Purchase(nextId, clientName, selectedGame.get_identitykey(), seats, address);

            // Add the purchase using the service
            service.add_purchase(purchase);

            // Update game details (e.g., available seats) after the purchase is made
            updateGameDetails();

            showSuccessAlert("Purchase added successfully!");
            closeWindow();

        } catch (NumberFormatException e) {
            errorMessage.setText("Number of seats must be a valid number!");
        } catch (IllegalArgumentException e) {
            errorMessage.setText(e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void showSuccessAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void closeWindow() {
        Stage stage = (Stage) purchaseButton.getScene().getWindow();
        stage.close();
    }

    @Override
    public void notifyBoughtSeats(Game updatedGame) {
        // This method is called when the game object is updated (seats are purchased)

        // If this is the same game being observed, update the UI
        if (selectedGame.get_identitykey() == updatedGame.get_identitykey()) {
            selectedGame = updatedGame;

            // Update the game details on the UI
            Platform.runLater(() -> {
                // Update the UI components, such as available seats
                updateGameDetails();  // Calls the method to refresh the UI with updated seats
            });
        }
    }
}
