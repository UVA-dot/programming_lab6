package managers.commands;

import managers.CollectionManager;
import managers.Writer;
import models.Dragon;

public class Save extends Command{
    public Save(){
        super("Save", "Сохранить текущую коллекцию в файл");
    }
    @Override
    public String execute(String arg, Dragon dragon){
        return collectionManager.save();
    }
}
