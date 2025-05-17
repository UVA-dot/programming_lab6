package server.managers.commands;

import shared.models.Dragon;
import server.managers.CollectionManager;

public class Remove_by_id extends AbstractCommand {
    public Remove_by_id(){
        super("Remove_by_id", "Удаляет элемент по заданному id");
    }
    @Override
    public void execute(String data){
        CollectionManager collectionManager = CollectionManager.getData();
        collectionManager.remove_by_id(data);
    }
}
