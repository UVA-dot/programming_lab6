package managers.commands;

import managers.CollectionManager;
import models.Dragon;

public class Head extends Command {
    public Head(){
        super("Head","Выводит первый элемент коллекции");
    }
    @Override
    public String execute(String arg){
        return collectionManager.head();
    }
}
