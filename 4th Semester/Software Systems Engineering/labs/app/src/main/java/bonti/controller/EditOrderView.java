package bonti.controller;

import bonti.domain.Medicine;
import bonti.domain.Order;
import bonti.domain.Personnel;
import bonti.service.IService;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;

import java.util.HashMap;
import java.util.Map;

public class EditOrderView {

    private final IService service;
    private final Personnel personnel;
    private final Order order;

    public EditOrderView(IService service, Personnel personnel, Order order) {
        this.service = service;
        this.personnel = personnel;
        this.order = order;
    }

    public void show(Stage stage, Runnable onSuccess) {
        VBox root = new VBox(10);
        root.setPadding(new Insets(10));

        Label label = new Label("Modifică cantitatea medicamentelor:");

        // TableView cu medicamente și cantități
        TableView<Map.Entry<Medicine, Integer>> table = new TableView<>();
        ObservableList<Map.Entry<Medicine, Integer>> items = FXCollections.observableArrayList(order.getMedicines().entrySet());

        TableColumn<Map.Entry<Medicine, Integer>, String> medCol = new TableColumn<>("Medicament");
        medCol.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getKey().getName()));
        medCol.setEditable(false);

        TableColumn<Map.Entry<Medicine, Integer>, Integer> qtyCol = new TableColumn<>("Cantitate");
        qtyCol.setCellValueFactory(cell -> new SimpleIntegerProperty(cell.getValue().getValue()).asObject());
        qtyCol.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        qtyCol.setOnEditCommit(event -> {
            Map.Entry<Medicine, Integer> entry = event.getRowValue();
            int newQty = event.getNewValue();
            if (newQty > 0) {
                entry.setValue(newQty);
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Cantitatea trebuie să fie un număr pozitiv.");
                alert.showAndWait();
                table.refresh();
            }
        });

        table.setItems(items);
        table.getColumns().addAll(medCol, qtyCol);
        table.setEditable(true);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        Button saveBtn = new Button("Salvează modificările");
        saveBtn.setOnAction(e -> {
            Map<Medicine, Integer> updatedMedicines = new HashMap<>();
            for (Map.Entry<Medicine, Integer> entry : items) {
                updatedMedicines.put(entry.getKey(), entry.getValue());
            }

            order.setMedicines(updatedMedicines);

            service.updateOrder(order);

            onSuccess.run();
            stage.close();
        });

        root.getChildren().addAll(label, table, saveBtn);

        Scene scene = new Scene(root, 400, 300);
        stage.setScene(scene);
        stage.setTitle("Modifică Comandă");
        stage.show();
    }
}
