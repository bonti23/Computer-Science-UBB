package ro.mpp2024.controller;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import ro.mpp2024.Service;
import ro.mpp2024.*;

import java.io.IOException;
import java.util.List;

public class MainMenuView implements IObserver {
    private IService service;

    @FXML
    private ComboBox<Type> gameTypeComboBox;

    @FXML
    private TableView<Game> gameTable;

    @FXML
    private TableColumn<Game, String> teamAColumn;

    @FXML
    private TableColumn<Game, String> teamBColumn;

    @FXML
    private TableColumn<Game, String> dateColumn;

    @FXML
    private TableColumn<Game, Float> priceColumn;

    @FXML
    private TableColumn<Game, Integer> seatsColumn;

    @FXML
    private Button purchaseButton;

    @FXML
    private Button logoutButton;

    @FXML
    private ImageView backgroundImage;

    public void setService(IService service) {
        this.service = service;
        loadGames(); // Încarcă jocurile inițiale
    }

    @Override
    public void notifyBoughtSeats(Game updatedGame) {
        javafx.application.Platform.runLater(() -> {
            ObservableList<Game> currentGames = gameTable.getItems();
            for (int i = 0; i < currentGames.size(); i++) {
                Game game = currentGames.get(i);
                if (game.get_identitykey() != null && game.get_identitykey().equals(updatedGame.get_identitykey())) {
                    currentGames.set(i, updatedGame);  // Înlocuim jocul vechi cu cel actualizat
                    break;
                }
            }
            gameTable.refresh();  // Reîmprospătează tabelul
        });
    }

    @FXML
    void initialize() {
        gameTypeComboBox.setItems(FXCollections.observableArrayList(Type.values()));

        teamAColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTeamA()));
        teamBColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTeamB()));
        dateColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDate()));
        priceColumn.setCellValueFactory(cellData -> new SimpleFloatProperty(cellData.getValue().getPrice()).asObject());
        seatsColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getSeats()).asObject());

        gameTypeComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                updateGameTable(newValue);
            }
        });

        gameTable.setRowFactory(tv -> new TableRow<Game>() {
            @Override
            protected void updateItem(Game game, boolean empty) {
                super.updateItem(game, empty);
                if (game == null || empty) {
                    setStyle("");
                } else {
                    if (game.getSeats() == 0) {
                        setStyle("-fx-background-color: red;");  // Culoare roșie pentru jocurile fără locuri
                    } else {
                        setStyle("");
                    }
                }
            }
        });

        purchaseButton.setOnAction(event -> handlePurchase());
        logoutButton.setOnAction(event -> handleLogout());

        // Setează imaginea de fundal
        Image img = new Image(getClass().getResource("/images/Zu2zS6zcVnBUR5KGvlOgA12N2YMkZLvAeWb0rAsu.webp").toExternalForm());
        backgroundImage.setImage(img);
    }

    private void loadGames() {
        List<Game> games = service.show_games();
        for (Game g : games) {
            System.out.println("Game ID: " + g.get_identitykey()); // Ar trebui să NU fie null
        }
        gameTable.getItems().setAll(games);
    }

    @FXML
    private void handleLogout() {
        Stage currentStage = (Stage) logoutButton.getScene().getWindow();
        currentStage.close();

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/loginview.fxml"));
            Parent root = loader.load();

            LoginView loginStage = loader.getController();
            loginStage.setService(service);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Login");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void updateGameTable(Type gameType) {
        List<Game> games = service.showcase_games_by_type(gameType.name());
        ObservableList<Game> gameList = FXCollections.observableArrayList(games);
        gameTable.setItems(gameList);
    }

    private void refreshGameTable() {
        Type selectedType = gameTypeComboBox.getValue();
        if (selectedType != null) {
            updateGameTable(selectedType);
        }
    }

    @FXML
    private void handlePurchase() {
        // Obținem jocul selectat din tabel
        Game selectedGame = gameTable.getSelectionModel().getSelectedItem();

        if (selectedGame != null) {
            Alert idAlert = new Alert(Alert.AlertType.INFORMATION);
            idAlert.setTitle("Selected Game ID");
            idAlert.setHeaderText(null);
            idAlert.setContentText("Selected Game ID: " + selectedGame.get_identitykey());
            idAlert.showAndWait();
        }

        if (selectedGame == null) {
            showErrorMessage("Please select a game to purchase tickets.");
            return;
        }

        // Verificăm dacă există locuri disponibile
        if (selectedGame.getSeats() == 0) {
            showErrorMessage("There are no more tickets left.");
            return;
        }

        // Dacă avem locuri disponibile, continuăm cu achiziția
        try {
            // Încărcăm fereastra pentru achiziție
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/purchaseview.fxml"));
            Parent root = loader.load();

            PurchaseView purchaseController = loader.getController();
            purchaseController.setService(service);
            purchaseController.setGame(selectedGame);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Purchase Tickets");
            stage.setOnHidden(event -> refreshGameTable()); // Actualizăm tabela când fereastra este închisă
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showErrorMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Selection Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
