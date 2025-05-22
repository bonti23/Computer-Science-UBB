package bonti.controller;

import bonti.domain.*;
import bonti.service.IService;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Map;

public class ChemistView {

    private final IService service;
    private TableView<Order> tableView;
    private ObservableList<Order> orders;

    public ChemistView(IService service) {
        this.service = service;
    }

    public void show(Stage stage) {
        tableView = new TableView<>();

        TableColumn<Order, String> deadlineCol = new TableColumn<>("Deadline");
        deadlineCol.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getDeadline()));

        TableColumn<Order, String> sectionCol = new TableColumn<>("Section");
        sectionCol.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getSection().name()));

        TableColumn<Order, String> statusCol = new TableColumn<>("Status");
        statusCol.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getStatus().name()));

        tableView.getColumns().addAll(deadlineCol, sectionCol, statusCol);
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        tableView.setRowFactory(tv -> new TableRow<Order>() {
            @Override
            protected void updateItem(Order order, boolean empty) {
                super.updateItem(order, empty);
                if (order == null || empty) {
                    setStyle("");
                } else {
                    if (!isDeadlineValid(order.getDeadline())) {
                        setStyle("-fx-background-color: rgba(255,0,0,0.3);");
                    } else {
                        setStyle("");
                    }
                }
            }
        });

        Button acceptBtn = new Button("Acceptă Comanda");
        acceptBtn.setOnAction(e -> acceptOrder());

        Button rejectBtn = new Button("Respinge Comanda");
        rejectBtn.setOnAction(e -> rejectOrder());

        Button detailsBtn = new Button("Vezi Detalii");
        detailsBtn.setOnAction(e -> showDetails(stage));

        Button logoutBtn = new Button("Logout");
        logoutBtn.setOnAction(e -> {
            stage.close();
            showLogin();
        });

        HBox buttons = new HBox(10, acceptBtn, rejectBtn, detailsBtn, logoutBtn);
        buttons.setAlignment(Pos.CENTER);

        VBox root = new VBox(10, tableView, buttons);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.CENTER);

        Scene scene = new Scene(root, 750, 450);
        stage.setScene(scene);
        stage.setTitle("Farmacist - Vizualizare Comenzi");
        stage.show();

        loadOrders();
    }

    private void loadOrders() {
        List<Order> orderList = service.getAllOrders();
        System.out.println("Orders loaded: " + orderList.size());
        orders = FXCollections.observableArrayList(orderList);
        tableView.setItems(orders);
    }

    private boolean isDeadlineValid(String deadline) {
        try {
            LocalDate deadlineDate = LocalDate.parse(deadline, DateTimeFormatter.ISO_LOCAL_DATE);
            LocalDate today = LocalDate.now();
            return !deadlineDate.isBefore(today);
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    private void acceptOrder() {
        Order selected = tableView.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert(Alert.AlertType.WARNING, "Selectați o comandă pentru a o accepta.");
            return;
        }

        if (!isDeadlineValid(selected.getDeadline())) {
            showAlert(Alert.AlertType.WARNING, "Comanda nu poate fi acceptată deoarece deadline-ul a trecut.");
            return;
        }

        if (selected.getStatus() == OrderStatus.PENDING) {
            selected.setStatus(OrderStatus.ACCEPTED);
            service.updateOrder(selected);
            tableView.refresh();
            showAlert(Alert.AlertType.INFORMATION, "Comanda a fost acceptată.");
        } else {
            showAlert(Alert.AlertType.WARNING, "Comanda nu poate fi acceptată.");
        }
    }

    private void rejectOrder() {
        Order selected = tableView.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert(Alert.AlertType.WARNING, "Selectați o comandă pentru a o respinge.");
            return;
        }

        if (!isDeadlineValid(selected.getDeadline())) {
            showAlert(Alert.AlertType.WARNING, "Comanda nu poate fi respinsă deoarece deadline-ul a trecut.");
            return;
        }

        if (selected.getStatus() == OrderStatus.PENDING) {
            selected.setStatus(OrderStatus.DECLINED);
            service.updateOrder(selected);
            tableView.refresh();
            showAlert(Alert.AlertType.INFORMATION, "Comanda a fost respinsă.");
        } else {
            showAlert(Alert.AlertType.WARNING, "Comanda nu poate fi respinsă.");
        }
    }

    private void showDetails(Stage parentStage) {
        Order selected = tableView.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert(Alert.AlertType.WARNING, "Selectează o comandă.");
            return;
        }

        System.out.println("Displaying details for order: " + selected.getDeadline());
        System.out.println("Medicines: " + selected.getMedicines());

        VBox detailsBox = new VBox(10);
        detailsBox.setPadding(new Insets(20));

        Label title = new Label("Comandă: " + selected.getDeadline());
        Label section = new Label("Secție: " + selected.getSection());
        Label status = new Label("Status: " + selected.getStatus());

        TableView<Map.Entry<Medicine, Integer>> itemTable = new TableView<>();
        TableColumn<Map.Entry<Medicine, Integer>, String> medCol = new TableColumn<>("Medicament");
        medCol.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getKey().getName()));

        TableColumn<Map.Entry<Medicine, Integer>, Integer> qtyCol = new TableColumn<>("Cantitate");
        qtyCol.setCellValueFactory(cell -> new SimpleIntegerProperty(cell.getValue().getValue()).asObject());

        itemTable.getColumns().addAll(medCol, qtyCol);
        itemTable.setItems(FXCollections.observableArrayList(selected.getMedicines().entrySet()));
        itemTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        detailsBox.getChildren().addAll(title, section, status, itemTable);

        Stage dialog = new Stage();
        dialog.setTitle("Detalii Comandă");
        dialog.setScene(new Scene(detailsBox, 500, 400));
        dialog.initOwner(parentStage);
        dialog.showAndWait();
    }

    private void showLogin() {
        Stage loginStage = new Stage();
        LoginView loginView = new LoginView();
        loginView.show(loginStage, service);
    }

    private void showAlert(Alert.AlertType type, String msg) {
        Alert alert = new Alert(type);
        alert.setTitle("Informație");
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
