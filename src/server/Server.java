package server;

import connection.Connection;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.LinkedList;

public class Server {
    public static final int PORT = 8081;
    public static LinkedList<Connection> connections = new LinkedList<>();

    public static void main(String args[]) {
        new Server();
    }

    private Server() {
        System.out.println("Server running...");
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (!serverSocket.isClosed()) {
                try {
                    Connection connection = new Connection(serverSocket.accept());
                    connections.add(connection);
                    connection.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
