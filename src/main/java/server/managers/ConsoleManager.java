package server.managers;
import server.interfaces.Console;
import server.managers.commands.AbstractCommand;
import shared.models.Dragon;

import java.time.LocalDateTime;
import java.util.Scanner;

public class ConsoleManager implements Console{
    private Scanner scanner;
    private CommandManager commandManager;
    private Validator validator;
    private CollectionManager collectionManager = CollectionManager.getData();
    public ConsoleManager(Scanner scanner, CommandManager commandManager, Validator validator){
        this.commandManager = commandManager;
        this.scanner = scanner;
        this.validator = validator;
    }
    public Integer setId(){
        Integer id = 0;
        for(Dragon dragon: collectionManager.getCollection()){
            id = Math.max(id, dragon.getId());
        }
        return id + 1;
    }
    public LocalDateTime setTime(){
        return LocalDateTime.now();
    }
    public String inputFieldString(){
        String curField = null;
        if(scanner.hasNextLine()){
            curField = scanner.nextLine().trim();
        }
        return curField;
    }
    public Integer inputFieldNumber(){
        String curField = null;
        try {
            if (scanner.hasNextLine()) {
                curField = scanner.nextLine().trim();
            }
            return Integer.parseInt(curField);
        }
        catch(NumberFormatException e){
            System.out.println("Введено не число");
        }
        return null;
    }
    @Override
    public void executeConsole(){
        String curcmd;
        while(scanner.hasNext()){
            String line = scanner.nextLine().trim();
            if(!line.isEmpty()){
                if(line.equalsIgnoreCase("EXIT")) break;
                else{
                    String[] input = line.split(" ");
                    curcmd = input[0];
                    AbstractCommand cmd = commandManager.get(curcmd);
                    if(cmd == null){
                        System.out.println("Команда не найдена");
                        commandManager.get("help").execute();
                    }
                    else{
                        commandManager.updateHistory(cmd);
                        boolean isArg = false;
                        String args = null;
                        try{
                            args = input[1];
                            isArg = true;
                            cmd.setScanner(scanner);
                        }
                        catch (ArrayIndexOutOfBoundsException e){
                        }
                        if(isArg){
                            try{
                                cmd.execute(args);
                            }
                            catch (ArrayIndexOutOfBoundsException e){
                                System.out.println("Команда не подразумевает аргументов");
                            }
                        }
                        else{
                            try{
                                if(cmd.executeScanner()){
                                    cmd.execute(scanner);
                                }
                                else{
                                    cmd.execute();
                                }
                            }
                            catch (ArrayIndexOutOfBoundsException e){
                                System.out.println("Ожидались параметры для этой команды");
                            }
                        }
                    }
                }
            }
        }
    }
}
