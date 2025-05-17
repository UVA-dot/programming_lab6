package managers.commands;

import managers.CommandManager;
import models.Dragon;
import managers.CollectionManager;

import java.util.Scanner;

public class Update_id extends Command {
    public Update_id(){
        super("Update_id", "Обновить значение элемента коллекции, id которого равен введённому параметру");
        flagscanner = true;
    }

    @Override
    public String execute(String arg) throws ArrayIndexOutOfBoundsException {
        return collectionManager.update_id(arg);
    }
}
