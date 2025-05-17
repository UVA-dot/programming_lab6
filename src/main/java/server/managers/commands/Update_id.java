package server.managers.commands;

import managers.CommandManager;
import models.Dragon;
import managers.CollectionManager;

import java.util.Scanner;

public class Update_id extends AbstractCommand {
    public Update_id(){
        super("Update_id", "Обновить значение элемента коллекции, id которого равен введённому параметру");
        flagscanner = true;
    }

    @Override
    public void execute(Scanner scanner) throws ArrayIndexOutOfBoundsException {
        CollectionManager collectionManager = CollectionManager.getData();
        collectionManager.update_id(scanner);
    }
}
