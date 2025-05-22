package bonti.controller;

import bonti.IService;
import bonti.model.Game;
import bonti.model.Purchase;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.List;

public class PurchaseView {
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

            List<Purchase> all = service.allPurchases();
            Long new_id = all.stream()
                    .mapToLong(Purchase::getId)
                    .max()
                    .orElse(0L) + 1;
            Purchase purchase = new Purchase(new_id, clientName, selectedGame.getId(), seats, address);
            service.addPurchase(purchase);

            showSuccessAlert("purchase added successfully!");
            closeWindow();

        } catch (NumberFormatException e) {
            errorMessage.setText("number of seats must be a valid number!");
        } catch (IllegalArgumentException e) {
            errorMessage.setText(e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e);
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