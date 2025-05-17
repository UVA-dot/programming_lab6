package managers.commands;

import managers.CommandManager;
import managers.commands.Command;
import models.Dragon;

public class Help extends Command {
    public Help(){
        super("Help","Вывести справку по доступным командам");
    }
    @Override
    public String execute(String arg){
        return collectionManager.help();
    }
}
