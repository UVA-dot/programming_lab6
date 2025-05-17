package server.managers.commands;

import server.managers.CollectionManager;
import shared.models.Dragon;

public class Filter_less_than_character extends Command {
    public Filter_less_than_character(){
        super("Filter_less_than_character", "вывести элементы, значение поля character которых меньше заданного");
    }
    @Override
    public void execute(String data){
        CollectionManager collectionManager = CollectionManager.getData();
        collectionManager.filter(data);
    }
}
