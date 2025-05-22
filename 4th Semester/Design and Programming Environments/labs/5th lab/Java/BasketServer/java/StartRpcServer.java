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
        Properties props = new Properties();
        try {
            props.load(StartRpcServer.class.getResourceAsStream("/server.properties"));
            System.out.println("Loaded bd.config successfully.");
        } catch (IOException e) {
            System.err.println("Cannot find bd.config: " + e);
            return;
        }

        JdbcUtils jdbcUtils = new JdbcUtils(props);

        RepositoryDBGame gameRepo = new RepositoryDBGame(jdbcUtils);
        RepositoryDBUser userRepo = new RepositoryDBUser(jdbcUtils);
        RepositoryDBPurchase purchaseRepo = new RepositoryDBPurchase(jdbcUtils);

        IService service = new Service(gameRepo, userRepo, purchaseRepo);

        int serverPort = defaultPort;
        try {
            serverPort = Integer.parseInt(props.getProperty("server.port"));
        } catch (NumberFormatException e) {
            System.err.println("Invalid port number. Using default port: " + defaultPort);
        }

        System.out.println("Starting server on port: " + serverPort);

        AbstractServer server = new BasketRpcConcurrentServer(serverPort, service);

        try {
            server.start();
        } catch (ro.mpp2024.utils.ServerException e) {
            System.err.println("Error starting the server: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                server.stop();
            } catch (ro.mpp2024.utils.ServerException e) {
                System.err.println("Error stopping the server: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
}
