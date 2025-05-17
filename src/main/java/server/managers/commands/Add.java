package server.managers.commands;

import server.managers.CollectionManager;

import java.util.Scanner;

public class Add extends Command {
    public Add(){
        super("Add", "Добавить новый элемент в коллекцию");
        flagscanner = true;
    }
    @Override
    public void execute(Scanner scanner){
        CollectionManager collectionManager = CollectionManager.getData();
        collectionManager.add(scanner);
    }
}
