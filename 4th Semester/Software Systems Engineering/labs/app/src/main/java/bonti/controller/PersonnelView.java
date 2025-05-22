package bonti.controller;

import bonti.domain.Personnel;
import bonti.service.IService;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class PersonnelView {

    private final IService service;
    private final Personnel personnel;

    public PersonnelView(IService service, Personnel personnel) {
        this.service = service;
        this.personnel = personnel;
    }

    public void show(Stage stage) {
        Button seeDrugsButton = new Button("See Drugs");
        Button createOrderButton = new Button("Create an Order");

        double buttonWidth = 150;
        seeDrugsButton.setPrefWidth(buttonWidth);
        createOrderButton.setPrefWidth(buttonWidth);

        String buttonStyle = "-fx-background-color: #545ae2; -fx-text-fill: white; -fx-padding: 10px;";
        seeDrugsButton.setStyle(buttonStyle);
        createOrderButton.setStyle(buttonStyle);

        applyHoverEffect(seeDrugsButton);
        applyHoverEffect(createOrderButton);

        seeDrugsButton.setOnAction(e -> {
            stage.close();
            DrugsView drugsView = new DrugsView(service, personnel);
            Stage drugsStage = new Stage();
            drugsView.show(drugsStage);
        });

        createOrderButton.setOnAction(e -> {
            stage.close();
            OrderView orderView = new OrderView(service, personnel);
            Stage orderStage = new Stage();
            orderView.show(orderStage);
        });

        Button logoutButton = new Button("Logout");
        logoutButton.setStyle(buttonStyle);
        logoutButton.setOnAction(e -> {
            stage.close();
            openLoginWindow();
        });

        HBox buttonBox = new HBox(20, seeDrugsButton, createOrderButton);
        buttonBox.setAlignment(Pos.CENTER);

        VBox layout = new VBox(30, buttonBox, logoutButton);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(30));

        Scene scene = new Scene(layout, 400, 250);
        stage.setTitle("Personnel Options");
        stage.setScene(scene);
        stage.show();
    }

    private void openLoginWindow() {
        Stage loginStage = new Stage();
        LoginView loginView = new LoginView();
        loginView.show(loginStage, service);
    }

    private void applyHoverEffect(Button button) {
        DropShadow shadow = new DropShadow();
        shadow.setRadius(5);
        shadow.setOffsetX(0);
        shadow.setOffsetY(3);
        shadow.setColor(Color.color(0.3, 0.3, 0.3));

        button.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> {
            button.setScaleX(1.1);
            button.setScaleY(1.1);
            button.setEffect(shadow);
        });

        button.addEventHandler(MouseEvent.MOUSE_EXITED, e -> {
            button.setScaleX(1.0);
            button.setScaleY(1.0);
            button.setEffect(null);
        });
    }
}
