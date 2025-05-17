package server.managers.commands;

import server.managers.CommandManager;

public class History extends AbstractCommand {
    public History(){
        super("History", "Выводит последние 10 команд(без аргументов)");
    }
    @Override
    public void execute(){
        CommandManager commandManager = CommandManager.getCommandManager();
        commandManager.printHistory();
    }
}
