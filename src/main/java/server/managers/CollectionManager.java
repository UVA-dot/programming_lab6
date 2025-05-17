package server.managers;

import server.interfaces.Collectionable;
import server.managers.commands.Update_id;
import shared.models.*;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.Scanner;

public class CollectionManager implements Collectionable<Dragon> {
    private static final LocalDateTime localdatetime;

    static {
        localdatetime = LocalDateTime.now();
    }

    private static CollectionManager collection;
    private LinkedList<Dragon> dragons = new LinkedList<Dragon>();

    @Override
    public void setCollection(LinkedList<Dragon> dragons) {
        this.dragons = dragons;
    }

    @Override
    public LinkedList<Dragon> getCollection() {
        return dragons;
    }

    private CollectionManager() {
    }

    @Override
    public void printInfo() {
        System.out.println("Тип коллекции: " + dragons.getClass().getName() + "\nДата инициализации: " + localdatetime + "\nКоличество элементов: " + dragons.size());
    }

    @Override
    public void print() {
        if (dragons.size() == 0) {
            System.out.println("Коллекция пуста");
        }
        for (Dragon dragon : dragons) {
            System.out.println("Дракон " + dragon.getId() + " {");
            System.out.println(dragon.toString());
            System.out.println("}");
        }
    }

    @Override
    public void clear() {
        dragons.clear();
        System.out.println("Коллекция очищена успешно!\n");
    }

    public void max_by_weight() {
        Dragon max_weight_dragon = collection.getCollection().getFirst();
        Long max_weight = collection.getCollection().getFirst().getWeight();
        for (Dragon dragon : collection.getData().getCollection()) {
            if (dragon.getWeight() > max_weight) {
                max_weight_dragon = dragon;
                max_weight = dragon.getWeight();
            }
        }
        System.out.println(max_weight_dragon.toString());
    }

    public void head() {
        if (collection.getCollection() != null) {
            System.out.println("1-ый элемент коллекции {");
            System.out.println(collection.getCollection().getFirst().toString());
            System.out.println("}");
        } else {
            System.out.println("Коллекция пуста");
        }
    }

    public void group_by() {
        for (DragonType cnt : DragonType.values()) {
            Integer value = 0;
            System.out.println("Вывод драконов типа " + cnt.toString() + " {");
            for (Dragon dragon : collection.getCollection()) {
                if (dragon.getType() == cnt) {
                    System.out.println(dragon.toString());
                    value++;
                }
            }
            System.out.println("}\nДраконов типа " + cnt.toString() + ": " + value + "\n");
        }
    }

    public void filter(String data) {
        boolean found = false;
        try {
            Integer value = Integer.parseInt(data);
            for (Dragon dragon : collection.getCollection()) {
                if (dragon.getCharacter() != null && dragon.getCharacter().ordinal() < value) {
                    System.out.println(dragon.toString());
                    found = true;
                }
            }
            if (!found) {
                System.out.println("Дракона с меньшим значением характера не найдено");
            }
        } catch (NumberFormatException e) {
            System.out.println("Параметр команды должен быть числом");
        }
    }

    public void remove_head() {
        if (collection.getData() != null) {
            System.out.println("1-ый элемент коллекции {");
            System.out.println(collection.getCollection().getFirst().toString());
            Dragon dragon = collection.getCollection().getFirst();
            System.out.println("}");
            collection.remove(dragon);
            System.out.println("Успешно удалён");
        } else {
            System.out.println("Коллекция пуста, показывать и удалять нечего");
        }
    }

    public void remove_by_id(String data) {
        try {
            Integer id = Integer.parseInt(data);
            Dragon dragon = collection.search(id);
            if (dragon != null) {
                collection.remove(dragon);
                System.out.println("Элемент с id = " + id + " успешно удалён!");
            }
        } catch (NumberFormatException e) {
            System.out.println("ID должен быть целым числом");
        } catch (NullPointerException ignored) {
        }
    }

    @Override
    public Dragon search(int id) {
        for (Dragon dragon : dragons) {
            if (id == dragon.getId()) {
                return dragon;
            }
        }
        System.out.println("Объекта с таким id не найдено в коллекции");
        return null;
    }

    @Override
    public void remove(Dragon dragon) {
        dragons.remove(dragon);
    }

    public static CollectionManager getData() {
        if (collection == null) {
            collection = new CollectionManager();
        }
        return collection;
    }

    public void update_id(Scanner scanner) throws NumberFormatException {
        Validator validator = new Validator();
        Dragon dragon = new Dragon();
        DragonHead dragonHead = new DragonHead();
        Coordinates coordinates = new Coordinates();
        ConsoleManager consoleManager = new ConsoleManager(scanner, CommandManager.getCommandManager(), validator);
        Dragon olddragon;
        while (true) {
            System.out.println("Введите id элемента, данные которого хотите обновить");
            Integer id = Integer.parseInt(String.valueOf(consoleManager.inputFieldNumber()));
            if (collection.search(id) != null) {
                olddragon = collection.search(id);
                dragon.setId(id);
                break;
            }
            System.out.println("Элемента с таким id нет в коллекции");
        }
        dragon.setCreationDate(olddragon.getCreationDate());
        while (true) {
            System.out.println("Введите поле name элемента: ");
            String name = consoleManager.inputFieldString();
            if (validator.validatingName(name)) {
                dragon.setName(name);
                break;
            }
            System.out.println("Поле не может быть пустым: ");
        }
        while (true) {
            System.out.println("Введите поле x: ");
            try {
                long x = Long.parseLong(String.valueOf(consoleManager.inputFieldNumber()));
                if (validator.validatingX(x)) {
                    coordinates.setX(x);
                    break;
                }
                System.out.println("Поле должно быть числом, максимальное значение поля 353");
            } catch (NumberFormatException e) {

            }
        }
        while (true) {
            System.out.println("Введите поле y:");
            Integer y = consoleManager.inputFieldNumber();
            if (validator.validatingY(y)) {
                coordinates.setY(y);
                break;
            }
            System.out.println("Поле не может быть null");
        }
        dragon.setCoordinates(coordinates);
        while (true) {
            System.out.println("Введите поле age: ");
            Integer age = consoleManager.inputFieldNumber();
            if (validator.validatingAge(age)) {
                dragon.setAge(age);
                break;
            }
            System.out.println("Поле должно быть больше 0 и не может быть null");
        }
        while (true) {
            System.out.println("Введите поле weight: ");
            try {
                Long weight = Long.parseLong(String.valueOf(consoleManager.inputFieldNumber()));
                if (validator.validatingWeight(weight)) {
                    dragon.setWeight(weight);
                    break;
                }
                System.out.println("Поле должно быть больше 0");
            } catch (NumberFormatException e) {
            }
        }
        while (true) {
            System.out.println("Введите поле type('WATER', 'FIRE', 'AIR'): ");
            String type = consoleManager.inputFieldString().toUpperCase();
            if (validator.validatingType(type)) {
                dragon.setType(DragonType.valueOf(type));
                break;
            }
            System.out.println("Значения поля нет в enum DragonType");
        }
        while (true) {
            System.out.println("Введите поле character('EVIL', 'CUNNING', 'WISE', 'CHAOTIC', 'FICKLE'): ");
            String character = consoleManager.inputFieldString().toUpperCase();
            if (validator.validatingChar(character)) {
                dragon.setCharacter(DragonCharacter.valueOf(character));
                break;
            }
            System.out.println("Значения поля нет в enum DragonCharacter");
        }
        while (true) {
            try {
                System.out.println("Введите поле size");
                Float size = Float.parseFloat(String.valueOf(consoleManager.inputFieldNumber()));
                dragonHead.setSize(size);
                dragon.setHead(dragonHead);
                break;
            } catch (NumberFormatException e) {
                System.out.println("Значение поля должно быть числом");
            }
        }
        collection.remove(olddragon);
        collection.put(dragon);
        System.out.println("Новый дракон успешно добавлен в коллекцию");
    }


    public void add(Scanner scanner){
        Validator validator = new Validator();
        Dragon dragon = new Dragon();
        DragonHead dragonHead = new DragonHead();
        Coordinates coordinates = new Coordinates();
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
        collection.put(dragon);
        System.out.println("Дракон успешно добавлен в коллекцию!");
    }
    @Override
    public void put(Dragon dragon){
        dragons.add(dragon);
        System.out.println("Dragon " + dragon.getName() + " добавлен в коллекцию");
    }
}
