package server;

import server.network.ServerConnection;

public class ServerMain {
    public static void main(String[] args) {
        ServerConnection server = new ServerConnection(5050);
        server.start();
    }
}