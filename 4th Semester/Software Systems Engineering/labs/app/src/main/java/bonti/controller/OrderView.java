package bonti.controller;

import bonti.domain.*;
import bonti.service.IService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleIntegerProperty;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

public class OrderView {

    private final IService service;
    private final Personnel personnel;

    private TableView<Order> tableView;
    private ObservableList<Order> orders;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public OrderView(IService service, Personnel personnel) {
        this.service = service;
        this.personnel = personnel;
    }

    public void show(Stage stage) {
        tableView = new TableView<>();

        TableColumn<Order, String> dateCol = new TableColumn<>("Deadline");
        dateCol.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getDeadline()));

        TableColumn<Order, String> sectionCol = new TableColumn<>("Section");
        sectionCol.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getSection().name()));

        TableColumn<Order, String> statusCol = new TableColumn<>("Status");
        statusCol.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getStatus().name()));

        tableView.getColumns().addAll(dateCol, sectionCol, statusCol);
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        highlightInvalidRows();

        Button detailsButton = new Button("Vezi Detalii");
        detailsButton.setOnAction(e -> showDetails(stage));

        Button addButton = new Button("Adaugă Comandă");
        addButton.setOnAction(e -> addOrder());

        Button editButton = new Button("Modifică Comandă");
        editButton.setOnAction(e -> editOrder());

        Button deleteButton = new Button("Șterge Comandă");
        deleteButton.setOnAction(e -> deleteOrder());

        Button backButton = new Button("Înapoi");
        backButton.setOnAction(e -> {
            stage.close();
            openPersonnelView();
        });

        HBox buttons = new HBox(10, backButton, addButton, editButton, deleteButton, detailsButton);
        buttons.setAlignment(Pos.CENTER);

        VBox layout = new VBox(15, tableView, buttons);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout, 800, 450);
        stage.setTitle("Comenzi Terminal " + personnel.getTerminal().name());
        stage.setScene(scene);
        stage.show();

        loadOrders();
    }

    private void loadOrders() {
        List<Order> orderList = service.getOrdersForTerminal(personnel.getTerminal());
        orders = FXCollections.observableArrayList(orderList);
        tableView.setItems(orders);
    }

    private void highlightInvalidRows() {
        tableView.setRowFactory(tv -> new TableRow<>() {
            @Override
            protected void updateItem(Order order, boolean empty) {
                super.updateItem(order, empty);
                if (order == null || empty) {
                    setStyle("");
                } else {
                    LocalDate deadline = LocalDate.parse(order.getDeadline(), formatter);
                    if (order.getStatus() == OrderStatus.PENDING && deadline.isBefore(LocalDate.now())) {
                        setStyle("-fx-background-color: #ffcccc;");
                    } else {
                        setStyle("");
                    }
                }
            }
        });
    }

    private void showDetails(Stage parentStage) {
        Order selected = tableView.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert(Alert.AlertType.WARNING, "Selectează o comandă.");
            return;
        }

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
        dialog.show();
    }

    private void addOrder() {
        Stage dialog = new Stage();
        AddOrderView addView = new AddOrderView(service, personnel);
        addView.show(dialog, this::loadOrders);
    }


    private void editOrder() {
        Order selected = tableView.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert(Alert.AlertType.WARNING, "Selectează o comandă pentru a o modifica.");
            return;
        }

        LocalDate deadline = LocalDate.parse(selected.getDeadline(), formatter);
        if (selected.getStatus() != OrderStatus.PENDING || deadline.isBefore(LocalDate.now())) {
            showAlert(Alert.AlertType.WARNING, "Comanda nu mai poate fi modificată.");
            return;
        }

        Stage dialog = new Stage();
        EditOrderView editView = new EditOrderView(service, personnel, selected);
        editView.show(dialog, this::loadOrders);
    }


    private void deleteOrder() {
        Order selected = tableView.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert(Alert.AlertType.WARNING, "Selectează o comandă pentru a o șterge.");
            return;
        }

        LocalDate deadline = LocalDate.parse(selected.getDeadline(), formatter);
        if (selected.getStatus() != OrderStatus.PENDING || deadline.isBefore(LocalDate.now())) {
            showAlert(Alert.AlertType.WARNING, "Comanda nu mai poate fi ștearsă.");
            return;
        }

        service.deleteOrder(selected.getId());
        orders.remove(selected);
        showAlert(Alert.AlertType.INFORMATION, "Comanda a fost ștearsă.");
    }

    private void openPersonnelView() {
        Stage personnelStage = new Stage();
        PersonnelView personnelView = new PersonnelView(service, personnel);
        personnelView.show(personnelStage);
    }

    private void showAlert(Alert.AlertType type, String msg) {
        Alert alert = new Alert(type);
        alert.setTitle("Informație");
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
