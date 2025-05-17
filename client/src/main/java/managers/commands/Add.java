package managers.commands;

import managers.CollectionManager;
import managers.Validator;
import models.Dragon;

import java.util.Scanner;

public class Add extends Command {
    public Add(){
        super("Add", "Добавить новый элемент в коллекцию");
        flagscanner = true;
    }
    @Override
    public String execute(String arg){
        return collectionManager.add();
    }
}
