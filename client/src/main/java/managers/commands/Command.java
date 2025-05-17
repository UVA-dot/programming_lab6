package managers.commands;

import interfaces.Commandable;
import managers.CollectionManager;
import managers.CommandManager;
import models.Dragon;

import java.util.Scanner;

public abstract class Command {
    private String name;
    private String description;
    protected boolean flagscanner = false;
    protected Scanner scanner = new Scanner(System.in);
    protected CollectionManager collectionManager = CollectionManager.getData();
    protected CommandManager commandManager = CommandManager.getCommandManager();
    public Command(String name, String description){
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }
    public String getDescription(){
        return description;
    }
    public Scanner getScanner(){
        return scanner;
    }
    public void setScanner(Scanner scanner){
        this.scanner = scanner;
    }
    public boolean executeScanner(){
        return flagscanner;
    }
    public String execute() throws ArrayIndexOutOfBoundsException{
        throw new ArrayIndexOutOfBoundsException();
    }
    public String execute(String arg) throws ArrayIndexOutOfBoundsException{
        throw new ArrayIndexOutOfBoundsException();
    }
}
