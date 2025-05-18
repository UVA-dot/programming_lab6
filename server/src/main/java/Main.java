import managers.*;
import models.Dragon;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.logging.Logger;

public class Main {
    private static Logger logger = Logger.getGlobal();
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        if (args.length < 1) {
            System.out.println("Введите путь к файлу csv в аргумент командной строки");
        } else {
            CollectionManager collectionManager = CollectionManager.getData();
            LinkedList<Dragon> result;
            String FilePath = args[0];
            Reader fileReader = new Reader(FilePath);
            result = fileReader.readCollection();
            collectionManager.setCollection(result);
            Scanner scanner = new Scanner(System.in);
            CommandManager commandManager = CommandManager.getCommandManager();
            Validator validator = new Validator();
            ServerManager consoleManager = new ServerManager(scanner, commandManager, validator);
            consoleManager.executeServer();
        }
    }
}

