package server.managers;

import server.managers.commands.AbstractCommand;
import server.managers.commands.Command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommandManager {
    private final Map<String, AbstractCommand> commands = new HashMap<>();
    private static CommandManager commandManager;
    public void register(String name, AbstractCommand command) {
        commands.put(name, command);
    }
    private static List<Command> latestCommands = new ArrayList<>();
    public AbstractCommand get(String name) {
        return commands.get(name);
    }
    public void updateHistory(Command cmd){
        if(latestCommands.size() >= 10) latestCommands = latestCommands.subList(0, 9);
        latestCommands.add(cmd);
    }
    public boolean has(String name) {
        return commands.containsKey(name);
    }
    public static CommandManager getCommandManager(){
        if(commandManager == null){
            commandManager = new CommandManager();
        }
        return commandManager;
    }
    public String execute(String name, String[] args, CollectionManager collection) {
        AbstractCommand cmd = get(name);
        if (cmd == null) {
            return "Команда '" + name + "' не найдена.";
        }
        return cmd.execute(args, collection);
    }
}