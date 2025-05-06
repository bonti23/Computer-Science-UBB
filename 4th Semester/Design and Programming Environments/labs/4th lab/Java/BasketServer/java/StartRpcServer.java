package ro.mpp2024;

import ro.mpp2024.jdbc.JdbcUtils;
import ro.mpp2024.jdbc.RepositoryDBGame;
import ro.mpp2024.jdbc.RepositoryDBPurchase;
import ro.mpp2024.jdbc.RepositoryDBUser;
import ro.mpp2024.utils.AbstractServer;
import ro.mpp2024.utils.BasketRpcConcurrentServer;

import java.io.FileReader;
import java.io.IOException;
import java.rmi.ServerException;
import java.util.Properties;

public class StartRpcServer {
    private static final int defaultPort = 55555;

    public static void main(String[] args) {
        // Load the properties file
        Properties props = new Properties();
        try {
            props.load(StartRpcServer.class.getResourceAsStream("/server.properties"));
            System.out.println("Loaded bd.config successfully.");
        } catch (IOException e) {
            System.err.println("Cannot find bd.config: " + e);
            return; // If config can't be loaded, exit the program.
        }

        JdbcUtils jdbcUtils = new JdbcUtils(props);

        // Initialize the repository classes using JdbcUtils
        RepositoryDBGame gameRepo = new RepositoryDBGame(jdbcUtils);
        RepositoryDBUser userRepo = new RepositoryDBUser(jdbcUtils);
        RepositoryDBPurchase purchaseRepo = new RepositoryDBPurchase(jdbcUtils);

        // Create the service with the repositories
        IService service = new Service(gameRepo, userRepo, purchaseRepo);

        // Get the server port from properties or use the default one
        int serverPort = defaultPort;
        try {
            serverPort = Integer.parseInt(props.getProperty("server.port"));
        } catch (NumberFormatException e) {
            System.err.println("Invalid port number. Using default port: " + defaultPort);
        }

        System.out.println("Starting server on port: " + serverPort);

        // Create and start the server
        AbstractServer server = new BasketRpcConcurrentServer(serverPort, service);

        // Handling exceptions thrown by server.start() and server.stop()
        try {
            server.start();  // This is where the exception might occur
        } catch (ro.mpp2024.utils.ServerException e) {
            System.err.println("Error starting the server: " + e.getMessage());
            e.printStackTrace(); // This will give more details about the exception
        } finally {
            try {
                server.stop();  // Attempt to stop the server even if there's an error
            } catch (ro.mpp2024.utils.ServerException e) {
                System.err.println("Error stopping the server: " + e.getMessage());
                e.printStackTrace(); // This will give more details if stopping fails
            }
        }
    }
}
