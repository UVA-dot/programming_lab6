package managers.commands;

import managers.CollectionManager;
import managers.commands.Command;
import models.Dragon;

public class Exit extends Command {
    public Exit(){
        super("Exit", "Выйти из приложения без сохранения коллекции в файл");
    }
    public String execute(String arg){
        return collectionManager.exit();
    }
}
