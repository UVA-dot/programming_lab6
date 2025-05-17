package server.managers.commands;

import shared.models.Dragon;
import server.managers.CollectionManager;
import shared.models.DragonType;

public class Group_counting_by_type extends Command {
    public Group_counting_by_type(){
        super("Group_counting_by_type", "сгруппировать элементы коллекции по значению поля type, вывести количество элементов в каждой группе");
    }
    @Override
    public void execute(){
        CollectionManager collectionManager = CollectionManager.getData();
        collectionManager.group_by();
    }
}
