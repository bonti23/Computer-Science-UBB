package ubb.scs.map.faptebune;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ubb.scs.map.faptebune.controller.LogInController;
import ubb.scs.map.faptebune.domain.validation.NevoieValidation;
import ubb.scs.map.faptebune.domain.validation.PersoanaValidation;
import ubb.scs.map.faptebune.repository.NevoieDBRepository;
import ubb.scs.map.faptebune.repository.PersoanaDBRepository;
import ubb.scs.map.faptebune.service.NevoieService;
import ubb.scs.map.faptebune.service.PersoanaService;

import java.io.IOException;

public class HelloApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        // Încarcă fișierul FXML pentru interfața de login
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ubb/scs/map/faptebune/login-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setTitle("LogIn");
        stage.setScene(scene);

        // Crează serviciile și repository-urile necesare
        PersoanaDBRepository persoanaDataBaseRepository = new PersoanaDBRepository(
                "jdbc:postgresql://localhost:5432/faptebune",
                "alexandrabontidean",
                "alexandramiha",
                new PersoanaValidation()
        );
        PersoanaService persoanaService = new PersoanaService(persoanaDataBaseRepository);

        NevoieDBRepository nevoieDataBaseRepository = new NevoieDBRepository(
                "jdbc:postgresql://localhost:5432/faptebune",
                "alexandrabontidean",
                "alexandramiha",
                new NevoieValidation()
        );
        NevoieService nevoieService = new NevoieService(nevoieDataBaseRepository);

        // Obține controllerul LogInController din FXML
        LogInController logInController = fxmlLoader.getController();

        // Setează serviciile înainte de a apela stage.show()
        logInController.setPersoanaService(persoanaService);
        logInController.setNevoieService(nevoieService);
        logInController.setLogInStage(stage);

        stage.show();
    }


    public static void main(String[] args) {
        launch();
    }
}
