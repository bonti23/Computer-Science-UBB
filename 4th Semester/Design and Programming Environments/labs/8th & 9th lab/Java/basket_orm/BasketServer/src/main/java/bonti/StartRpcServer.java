package bonti;

import bonti.utils.AbstractServer;
import bonti.utils.BasketRpcConcurrentServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class StartRpcServer {

    private static final int defaultPort = 55555;

    public static void main(String[] args) {
        // Start Spring Boot application and load the context
        ApplicationContext context = SpringApplication.run(StartRpcServer.class, args);

        System.out.println("Spring context started successfully.");

        // Get the repositories from the Spring context
        RepositoryGame gameRepo = context.getBean(RepositoryGame.class);
        RepositoryUser userRepo = context.getBean(RepositoryUser.class);
        RepositoryPurchase purchaseRepo = context.getBean(RepositoryPurchase.class);

        // Create the service instance using the repositories
        IService service = new Service(gameRepo, userRepo, purchaseRepo);

        // Get the port from application.properties or use the default
        int serverPort = defaultPort;
        try {
            serverPort = Integer.parseInt(context.getEnvironment().getProperty("server.port", "55555"));
        } catch (NumberFormatException e) {
            System.err.println("Invalid port. Using default: " + defaultPort);
        }

        System.out.println("Starting server on port: " + serverPort);

        // Initialize and start the server
        AbstractServer server = new BasketRpcConcurrentServer(serverPort, service);

        try {
            server.start();
        } catch (bonti.utils.ServerException e) {
            System.err.println("Error starting server: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                server.stop();
            } catch (bonti.utils.ServerException e) {
                System.err.println("Error stopping server: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
}
