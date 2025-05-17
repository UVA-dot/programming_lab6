package managers.commands;

import managers.CollectionManager;
import models.Dragon;

public class Show extends Command {
    public Show(){
        super("Show","Вывести все элементы коллекции в строковом представлении");
    }
    @Override
    public String execute(String arg, Dragon dragon){
        return collectionManager.print();
    }
}
