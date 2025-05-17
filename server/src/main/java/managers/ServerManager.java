package managers;
import interfaces.Serverable;
import managers.Server;
import managers.commands.Command;
import models.Dragon;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Scanner;

public class ServerManager implements Serverable {
    private Scanner scanner;
    private CommandManager commandManager;
    private Validator validator;
    private CollectionManager collectionManager = CollectionManager.getData();
    public ServerManager(Scanner scanner, CommandManager commandManager, Validator validator){
        this.commandManager = commandManager;
        this.scanner = scanner;
        this.validator = validator;
    }
    public Integer setId(){
        Integer id = 0;
        for(Dragon dragon: collectionManager.getCollection()){
            id = Math.max(id, dragon.getId());
        }
        return id + 1;
    }
    public LocalDateTime setTime(){
        return LocalDateTime.now();
    }
    public String inputFieldString(){
        String curField = null;
        if(scanner.hasNextLine()){
            curField = scanner.nextLine().trim();
        }
        return curField;
    }
    public Integer inputFieldNumber(){
        String curField = null;
        try {
            if (scanner.hasNextLine()) {
                curField = scanner.nextLine().trim();
            }
            return Integer.parseInt(curField);
        }
        catch(NumberFormatException e){
            System.out.println("Введено не число");
        }
        return null;
    }
    @Override
    public void executeServer() throws IOException, ClassNotFoundException {
        Server server = new Server("localhost", 5050);
        server.start();
    }
}
