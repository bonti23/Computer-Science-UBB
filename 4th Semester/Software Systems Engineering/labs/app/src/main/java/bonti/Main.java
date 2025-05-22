package bonti;

import bonti.controller.LoginView;
import bonti.service.IService;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

public class Main extends Application {

    private ConfigurableApplicationContext springContext;

    @Override
    public void init() {
        springContext = SpringApplication.run(SpringBootApp.class);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            IService service = springContext.getBean(IService.class);

            // Deschide 3 ferestre de login ca exemplu
            openLoginWindow(service, "Login 1");
            openLoginWindow(service, "Login 2");
            openLoginWindow(service, "Login 3");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Metodă care deschide o fereastră nouă cu login
    private void openLoginWindow(IService service, String title) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/loginview.fxml"));
        Parent root = loader.load();

        LoginView loginController = loader.getController();
        loginController.setService(service);

        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle(title);
        stage.show();
    }

    @Override
    public void stop() {
        springContext.close();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
