package bonti.controller;

import bonti.domain.*;
import bonti.service.IService;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddOrderView {

    private final IService service;
    private final Personnel personnel;

    public AddOrderView(IService service, Personnel personnel) {
        this.service = service;
        this.personnel = personnel;
    }

    public void show(Stage stage, Runnable onOrderAdded) {
        VBox root = new VBox(10);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.CENTER_LEFT);

        ComboBox<Section> sectionBox = new ComboBox<>(FXCollections.observableArrayList(Section.values()));
        sectionBox.setPromptText("Alege secția");

        DatePicker deadlinePicker = new DatePicker();
        deadlinePicker.setPromptText("Alege data limită");

        List<Medicine> medicines = service.getAllMedicines();
        Map<Medicine, TextField> quantityFields = new HashMap<>();

        VBox medicineInputs = new VBox(5);
        medicineInputs.setPadding(new Insets(10));

        for (Medicine med : medicines) {
            Label label = new Label(med.getName());
            TextField qtyField = new TextField();
            qtyField.setPromptText("Cantitate");

            quantityFields.put(med, qtyField);

            HBox row = new HBox(10, label, qtyField);
            medicineInputs.getChildren().add(row);
        }

        Button saveBtn = new Button("save");
        saveBtn.setOnAction(e -> {
            Section section = sectionBox.getValue();
            LocalDate deadline = deadlinePicker.getValue();

            if (section == null || deadline == null) {
                showAlert(Alert.AlertType.ERROR, "Toate câmpurile sunt obligatorii.");
                return;
            }

            Map<Medicine, Integer> orderContent = new HashMap<>();
            for (Map.Entry<Medicine, TextField> entry : quantityFields.entrySet()) {
                String text = entry.getValue().getText().trim();
                if (!text.isEmpty()) {
                    try {
                        int qty = Integer.parseInt(text);
                        if (qty > 0) {
                            orderContent.put(entry.getKey(), qty);
                        }
                    } catch (NumberFormatException ex) {
                        showAlert(Alert.AlertType.ERROR, "Cantitate invalidă pentru " + entry.getKey().getName());
                        return;
                    }
                }
            }

            if (orderContent.isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Adaugă cel puțin un medicament.");
                return;
            }

            Order order = Order.builder()
                    .deadline(deadline.toString())
                    .status(OrderStatus.PENDING)
                    .terminal(personnel.getTerminal())
                    .section(section)
                    .medicines(orderContent)
                    .build();
            service.addOrder(order);


            showAlert(Alert.AlertType.INFORMATION, "Comandă adăugată cu succes.");
            stage.close();
            onOrderAdded.run();
        });

        Button cancelBtn = new Button("decline");
        cancelBtn.setOnAction(e -> stage.close());

        HBox btnBox = new HBox(10, saveBtn, cancelBtn);
        btnBox.setAlignment(Pos.CENTER);

        root.getChildren().addAll(new Label("Secție:"), sectionBox, new Label("Deadline:"), deadlinePicker,
                new Label("Medicamente:"), medicineInputs, btnBox);

        stage.setScene(new Scene(root, 500, 600));
        stage.setTitle("Adaugă Comandă Nouă");
        stage.show();
    }

    private void showAlert(Alert.AlertType type, String msg) {
        Alert alert = new Alert(type);
        alert.setTitle("Informație");
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
