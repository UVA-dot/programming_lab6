package client;

import client.network.ClientConnection;
import shared.commands.CommandRequest;

public class ClientMain {
    public static void main(String[] args) {
        ClientConnection client = new ClientConnection("localhost", 5050);
        ConsoleManager console = new ConsoleManager();

        while (true) {
            String[] input = console.readCommand();
            String command = input[0];
            String arg = input.length > 1 ? input[1] : null;

            if (command.equals("exit")) break;

            CommandRequest request = new CommandRequest(command, arg);
            client.sendCommand(request);
        }
    }
}