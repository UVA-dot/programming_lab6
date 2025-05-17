package shared.commands;

import java.io.Serializable;

public class CommandRequest implements Serializable {
    private final String commandName;
    private final Object argument;

    public CommandRequest(String commandName, Object argument) {
        this.commandName = commandName;
        this.argument = argument;
    }

    public String getCommandName() {
        return commandName;
    }

    public Object getArgument() {
        return argument;
    }
}