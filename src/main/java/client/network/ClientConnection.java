package client.network;

import shared.commands.CommandRequest;
import shared.commands.CommandResponse;

import java.io.*;
import java.net.Socket;

public class ClientConnection {
    private final String host;
    private final int port;

    public ClientConnection(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void sendCommand(CommandRequest request) {
        try (Socket socket = new Socket(host, port);
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {

            out.writeObject(request);
            CommandResponse response = (CommandResponse) in.readObject();
            System.out.println("Ответ сервера: " + response.getResponse());

        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Ошибка при отправке команды: " + e.getMessage());
        }
    }
}