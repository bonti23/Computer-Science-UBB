package bonti.controller;

import bonti.domain.Medicine;
import bonti.domain.Personnel;
import bonti.service.IService;
import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;

public class DrugsView {

    private final IService service;
    private final Personnel personnel;

    public DrugsView(IService service, Personnel personnel) {
        this.service = service;
        this.personnel = personnel;
    }

    public void show(Stage stage) {
        TableView<Medicine> table = new TableView<>();

        TableColumn<Medicine, String> nameCol = new TableColumn<>("Medicine");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Medicine, String> prescriptionCol = new TableColumn<>("Prescription");
        prescriptionCol.setCellValueFactory(new PropertyValueFactory<>("prescription"));

        table.getColumns().addAll(nameCol, prescriptionCol);
        table.setItems(FXCollections.observableArrayList(service.getAllMedicines()));

        table.setStyle("-fx-border-color: #5a5a5a; -fx-border-width: 1px;");
        nameCol.setStyle("-fx-alignment: CENTER;");
        prescriptionCol.setStyle("-fx-alignment: CENTER;");
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        Button backButton = new Button("Înapoi");
        backButton.setStyle("-fx-background-color: #545ae2; -fx-text-fill: white; -fx-padding: 10px;");
        backButton.setOnAction(e -> {
            stage.close();
            new PersonnelView(service, personnel).show(new Stage());
        });

        VBox layout = new VBox(15, table, backButton);
        layout.setAlignment(Pos.TOP_CENTER);
        layout.setStyle("-fx-padding: 20px;");

        Scene scene = new Scene(layout, 600, 400);
        stage.setTitle("Drug List");
        stage.setScene(scene);
        stage.show();
    }
}
