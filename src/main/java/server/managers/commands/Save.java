package server.managers.commands;

import managers.Writer;

public class Save extends AbstractCommand {
    public Save(){
        super("Save", "Сохранить текущую коллекцию в файл");
    }
    @Override
    public void execute(){
        Writer write = new Writer();
        write.writeCollection();
    }
}
