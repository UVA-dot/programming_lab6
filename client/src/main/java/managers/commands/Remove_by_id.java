package managers.commands;

import models.Dragon;
import managers.CollectionManager;

public class Remove_by_id extends Command {
    public Remove_by_id(){
        super("Remove_by_id", "Удаляет элемент по заданному id");
    }
    @Override
    public String execute(String arg){
        return collectionManager.remove_by_id(arg);
    }
}
