package managers;

import managers.commands.Execute_script;
import managers.commands.Exit;
import models.Dragon;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.Scanner;

public class Controller {

    private String js = "";
    private int i = 1;
    public static String str ="";
    CommandManager commandManager = CommandManager.getCommandManager();
    public void run() throws FileNotFoundException {

        if (CommandControl.mode==1) {
            Execute_script execuiteScript = new Execute_script();
            execuiteScript.execute(null);
            return;
        }
        System.out.println("Введите команду:");
        //CollectionManager.cities =(converter.cityFromJson(tip));
        Scanner scanner =new Scanner(System.in);
        //System.out.println("Введите название файла:");
        CollectionManager manager = CollectionManager.getData();
        String command =null;
        try {
            command = scanner.nextLine().trim();
        }catch (java.util.NoSuchElementException e){
            Exit exit = new Exit();
            exit.execute("exit");
        }
        // Разделяем строку по пробелам
        String[] argument = command.split("\\s+");
        ArrayList<String> arrayList = new ArrayList<>(Arrays.asList(argument));
        String[] words=new String[0];
        String line="";
        int danger=0;
        int in=0;
        try{
            for(String str: arrayList){
                if ((str.equals(" ") || str.equals(""))&&(danger==0)){
                    if (str.equals(" ")){
                        arrayList.remove(in);
                    }
                    if (str.equals("")){
                        arrayList.remove(in);
                    }
                }else {
                    danger+=1;
                }
                in+=1;

            }
            words = arrayList.toArray(new String[0]);
            if (words.length > 1) {
                // Получаем первое слово
                command = words[0];
                String firstWord=words[0];

                // Удаляем пробелы перед следующими словами
                StringBuilder result = new StringBuilder("");
                for (int i = 1; i < words.length; i++) {
                    result.append(words[i]);
                    line = line + " " + words[i];
                }

                // Выводим результат
                line=line.trim();
            }else {
                command=words[0];
            }
        }catch (ConcurrentModificationException У){

        }

        //String command = scanner.nextLine();
        //WorkFile file = new WorkFile();
        command =command.toLowerCase();
        CollectionManager.command = command;

        String str;
        switch (command) {
            case "info","help","exit","show","clear","head","remove_head","max_by_weight", "history", "group_by":
                if (words.length==1){
                    str= (commandManager.getCommand(command).execute(command));
                }else {
                    System.out.println("Данная команда вводится без аргумента");
                    run();
                }

                break;
            case "filter","remove_by_id","update_id":
                if (words.length==2) {
                    str = (commandManager.getCommand(command).execute(line));
                } else if (words.length==1) {
                    System.out.println("Данная команда вводится с аргументом");
                    run();
                }else{
                    System.out.println("Данная команда вводится с одним аргументом");
                    run();
                }
                break;
            case "add":
                if (words.length==1){
                    str= (commandManager.getCommand(command).execute(command));
                }else {
                    System.out.println("Данная команда вводится без аргумента");
                    run();
                }

                break;

            case "execute_script":
                str = (commandManager.getCommand(command).execute(line));
                break;
            default:
                System.out.println(new IllegalArgumentException("Неизвестная команда: " + command));
                run();
                break;
        }
    }
}