package server.managers.commands;

import server.managers.CollectionManager;
import java.io.Serializable;

public abstract class AbstractCommand implements Serializable {
    public abstract void execute(String[] args, CollectionManager collection);
}