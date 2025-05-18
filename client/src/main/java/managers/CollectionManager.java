package managers;

import interfaces.Collectionable;
import managers.commands.Command;
import managers.commands.Update_id;
import models.*;
import request.CommandRequest;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class CollectionManager implements Collectionable {
    private static final LocalDateTime localdatetime;
    public static CommandRequest request;
    public static String command;
    private boolean flag = true;
    private static Long id = Long.parseLong("1");
    private WorkFile workFile = new WorkFile();
    private CommandControl control = new CommandControl();
    public static ArrayList<String> filesName = new ArrayList<String>();
    public static List<FilePathLengthCount> listPath = new ArrayList<>();
    public static int cycle = 0;
    static {
        localdatetime = LocalDateTime.now();
    }

    private static CollectionManager collection;

    private CollectionManager() {
    }

    @Override
    public String printInfo() {
        request = new CommandRequest("info");
        return null;
    }
    public String help(){
        request = new CommandRequest("help");
        return null;
    }
    @Override
    public String print() {
        request = new CommandRequest("show");
        return null;
    }

    @Override
    public String clear() {
        request = new CommandRequest("clear");
        return null;

    }
    public String execute_script(String path) {

        try {
            if (path != null) {
                CommandControl.inputData = path;
                CommandControl.count = 0;
                File file = new File(CommandControl.inputData);
                Scanner cv = new Scanner(file);
                workFile.reader(path);
                FilePathLengthCount linePath = new FilePathLengthCount(path, CommandControl.length, 0);
                listPath.add(linePath);
                for (String str : filesName) {
                    if (str.equals(path)) {
                        /*CommandControl.mode=0;
                        CommandControl.work=0;*/
                        for (FilePathLengthCount c : listPath) {
                            c.setLength(0);
                            flag = false;
                        }
                        if (flag == false) {
                            flag = true;
                            filesName.clear();
                            listPath.clear();
                            CommandControl.work=0;
                            CommandControl.mode=0;
                            CommandControl.is = -1;
                            System.out.println( "Произошло зацикливание. Один и тот же файл был вызван несколько раз");
                            Controller controller =new Controller();
                            controller.run();
                            return null;
                        }
                    }
                }
                filesName.add(path);
                CommandControl.is += 1;
            }
            CommandControl.work = 0;
            CommandControl.mode = 1;
            try {
                control.reader();
                if (listPath.size() == 1 && CollectionManager.listPath.get(CommandControl.is).getCount() >= CollectionManager.listPath.get(CommandControl.is).getLength()) {
                    CommandControl.mode = 0;
                    CommandControl.work = 0;
                    filesName.clear();
                    listPath.clear();
                    CommandControl.is = -1;
                }
                if (CollectionManager.listPath.get(CommandControl.is).getCount() >= CollectionManager.listPath.get(CommandControl.is).getLength()) {
                    listPath.remove(CommandControl.is);
                    filesName.remove(CommandControl.is);
                    CommandControl.is -= 1;
                }

            } catch (java.lang.IndexOutOfBoundsException E) {

            }
            if (flag == false) {
                if (listPath.size() == 0) {
                    flag = true;
                    filesName.clear();
                    listPath.clear();
                }
                return "Произошла зацикливание выполнение скрипта было остановлено";
            }

        } catch (java.io.FileNotFoundException e) {
            return "Скрипт не был найден";
        }
        return path;
    }
    public String exit(){
        System.exit(0);
        return "Выход из программы";
    }
    public String max_by_weight() {
        request = new CommandRequest("max_by_weight");
        return null;
    }

    public String head() {
        request = new CommandRequest("head");
        return null;
    }

    public String group_by() {
        request = new CommandRequest("group_by");
        return null;
    }

    public String filter(String data) {
        request = new CommandRequest("filter", data);
        return null;
    }

    public String remove_head() {
        request = new CommandRequest("remove_head");
        return null;
    }

    public String remove_by_id(String data) {
        request = new CommandRequest("remove_by_id", data);
        return null;
    }

    public static CollectionManager getData() {
        if (collection == null) {
            collection = new CollectionManager();
        }
        return collection;
    }

    public String update_id(String arg) throws NumberFormatException {
        try{
            id = Long.parseLong(arg);
            if(id <= 0){
                return "id не может быть меньше или равен 0";
            }


            }catch(NumberFormatException | NullPointerException | java.util.InputMismatchException e){
            return "Некорректный id";
        }
        return add();
    }


    public String add(){
        Validator validator = new Validator();
        Dragon dragon = new Dragon();
        DragonHead dragonHead = new DragonHead();
        Coordinates coordinates = new Coordinates();
        Scanner scanner = new Scanner(System.in);
        ConsoleManager consoleManager = new ConsoleManager(scanner, CommandManager.getCommandManager(), validator);
        dragon.setId(consoleManager.setId());
        dragon.setCreationDate(consoleManager.setTime());
        while(true) {
            System.out.println("Введите поле name элемента: ");
            String name = consoleManager.inputFieldString();
            if (validator.validatingName(name)) {
                dragon.setName(name);
                break;
            }
            System.out.println("Поле не может быть пустым: ");
        }
        while(true){
            System.out.println("Введите поле x: ");
            try {
                long x = Long.parseLong(String.valueOf(consoleManager.inputFieldNumber()));
                if (validator.validatingX(x)) {
                    coordinates.setX(x);
                    break;
                }
                System.out.println("Поле должно быть числом, максимальное значение поля 353");
            }
            catch (NumberFormatException e){

            }
        }
        while(true){
            System.out.println("Введите поле y:");
            Integer y = consoleManager.inputFieldNumber();
            if(validator.validatingY(y)){
                coordinates.setY(y);
                break;
            }
            System.out.println("Поле не может быть null");
        }
        dragon.setCoordinates(coordinates);
        while(true){
            System.out.println("Введите поле age: ");
            Integer age = consoleManager.inputFieldNumber();
            if(validator.validatingAge(age)){
                dragon.setAge(age);
                break;
            }
            System.out.println("Поле должно быть больше 0 и не может быть null");
        }
        while(true){
            System.out.println("Введите поле weight: ");
            try {
                Long weight = Long.parseLong(String.valueOf(consoleManager.inputFieldNumber()));
                if (validator.validatingWeight(weight)) {
                    dragon.setWeight(weight);
                    break;
                }
                System.out.println("Поле должно быть больше 0");
            }
            catch (NumberFormatException e){
            }
        }
        while(true){
            System.out.println("Введите поле type('WATER', 'FIRE', 'AIR'): ");
            String type = consoleManager.inputFieldString().toUpperCase();
            if(validator.validatingType(type)){
                dragon.setType(DragonType.valueOf(type));
                break;
            }
            System.out.println("Значения поля нет в enum DragonType");
        }
        while(true){
            System.out.println("Введите поле character('EVIL', 'CUNNING', 'WISE', 'CHAOTIC', 'FICKLE'): ");
            String character = consoleManager.inputFieldString().toUpperCase();
            if(validator.validatingChar(character)){
                dragon.setCharacter(DragonCharacter.valueOf(character));
                break;
            }
            System.out.println("Значения поля нет в enum DragonCharacter");
        }
        while(true) {
            try {
                System.out.println("Введите поле size");
                Float size = Float.parseFloat(String.valueOf(consoleManager.inputFieldNumber()));
                dragonHead.setSize(size);
                dragon.setHead(dragonHead);
                break;
            }
            catch (NumberFormatException e){
                System.out.println("Значение поля должно быть числом");
            }
        }
        request = new CommandRequest(command, dragon.getId(), dragon);
        return "Данные введены корректно";
    }
}
