package managers;

import models.Dragon;

import java.io.FileNotFoundException;

public class Controller {
    CommandManager commandManager = CommandManager.getCommandManager();
    public String run(String command, Dragon dragon, String line) throws FileNotFoundException {

        switch (command) {
            case "info", "help", "exit", "show", "clear", "save", "head", "remove_head", "min_by_capital":

                return (commandManager.getCommand(command).execute(command, dragon));

            case "remove_by_id", "update":
                return (commandManager.getCommand(command).execute(command, dragon));

            case "add_if_max", "add":

                return (commandManager.getCommand(command).execute(command, dragon));

            case "filter_starts_with_name":
                return (commandManager.getCommand(command).execute(line, dragon));
            case "execute_script":
//                System.out.println(control.getCommand(command).execution(line,null));
                break;
            case "count_less_than_climate":
                return (commandManager.getCommand(command).execute(line, dragon));

            default:

                break;


        }
        return "";
    }
}
