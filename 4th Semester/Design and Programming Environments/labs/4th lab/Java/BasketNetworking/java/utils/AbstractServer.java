package ro.mpp2024.utils;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public abstract class AbstractServer {
    private final int port;
    private ServerSocket server=null;
    public AbstractServer( int port){
        this.port=port;
    }

    public void start() throws ServerException {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            server = serverSocket;
            System.out.println("Server started on port " + port);
            while (true) {
                try {
                    Socket clientSocket = serverSocket.accept();
                    processRequest(clientSocket);
                } catch (IOException e) {
                    throw new ServerException("Error accepting client connection", e);
                }
            }
        } catch (IOException e) {
            throw new ServerException("Error starting the server", e);
        }
    }

    public void stop() throws ServerException {
        try {
            if (server != null && !server.isClosed()) {
                server.close();
                System.out.println("Server stopped successfully.");
            }
        } catch (Exception e) {
            throw new ServerException("Error stopping the server", e);
        }
    }

    protected abstract  void processRequest(Socket client);

}
