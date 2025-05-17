package server.network;

import shared.commands.CommandRequest;
import shared.commands.CommandResponse;
import server.managers.CollectionManager;
import server.managers.CommandManager;
import server.managers.commands.*;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.*;

public class ServerConnection {
    private final int port;
    private static final Logger logger = Logger.getLogger(ServerConnection.class.getName());
    private final CommandManager commandManager = new CommandManager();
    private final CollectionManager collectionManager = CollectionManager.getData();

    public ServerConnection(int port) {
        this.port = port;
        setupLogger();
        registerCommands();
    }

    private void registerCommands() {
        commandManager.register("help", new Help());
        commandManager.register("info", new Info());
        commandManager.register("show", new Show());
        commandManager.register("clear", new Clear());
        commandManager.register("exit", new Exit());
        // Зарегистрируй остальные команды аналогично
    }

    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            logger.info("Сервер запущен на порту " + port);

            while (true) {
                try (Socket clientSocket = serverSocket.accept();
                     ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());
                     ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream())) {

                    CommandRequest request = (CommandRequest) in.readObject();
                    logger.info("Получена команда: " + request.getCommandName());

                    String[] args = request.getArgument() == null ? new String[0] : request.getArgument().toString().split(" ");
                    String result = commandManager.execute(request.getCommandName(), args, collectionManager);

                    CommandResponse response = new CommandResponse(result);
                    out.writeObject(response);
                } catch (Exception e) {
                    logger.warning("Ошибка при обработке клиента: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            logger.severe("Ошибка запуска сервера: " + e.getMessage());
        }
    }

    private void setupLogger() {
        try {
            LogManager.getLogManager().reset();
            FileHandler fh = new FileHandler("server.log", true);
            fh.setFormatter(new SimpleFormatter());
            logger.addHandler(fh);
            logger.setLevel(Level.ALL);
        } catch (IOException e) {
            System.err.println("Не удалось настроить логирование: " + e.getMessage());
        }
    }
}