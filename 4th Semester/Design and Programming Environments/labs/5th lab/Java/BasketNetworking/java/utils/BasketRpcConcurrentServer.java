package ro.mpp2024.utils;

import ro.mpp2024.BasketClientRpcWorker;
import ro.mpp2024.IService;

import java.net.Socket;

public class BasketRpcConcurrentServer extends AbsConcurrentServer {
    private final IService basketServer;

    public BasketRpcConcurrentServer(int port, IService basketServer) {
        super(port);
        this.basketServer = basketServer;
        System.out.println("Basket - BasketRpcConcurrentServer started...");
    }

    @Override
    protected Thread createWorker(Socket client) {
        BasketClientRpcWorker worker = new BasketClientRpcWorker(basketServer, client);

        Thread tw = new Thread(worker);
        return tw;
    }

    @Override
    public void start() throws ServerException {
        try {
            super.start();
            System.out.println("Server started successfully on port: ");
        } catch (ServerException e) {
            System.err.println("Error starting server: " + e.getMessage());
            throw e;
        }
    }

    @Override
    public void stop() throws ServerException {
        try {
            super.stop();
            System.out.println("Server stopped successfully.");
        } catch (ServerException e) {
            System.err.println("Error stopping server: " + e.getMessage());
            throw e;
        }
    }
}
