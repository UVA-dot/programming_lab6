package managers;

import interfaces.Collectionable;
import managers.commands.Update_id;
import models.*;
import request.CommandRequest;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.Scanner;

public class CollectionManager implements Collectionable<Dragon>, Serializable {
    private static final LocalDateTime localdatetime;
    public static CommandRequest request;
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
    public String printInfo() {
        return ("Тип коллекции: " + dragons.getClass().getName() + "\nДата инициализации: " + localdatetime + "\nКоличество элементов: " + dragons.size());
    }

    @Override
    public String print() {
        if (dragons.size() == 0) {
            return ("Коллекция пуста");
        }
        return dragons.stream()
            .sorted((d1, d2) -> d1.getName().compareTo(d2.getName()))
            .map(dragon -> "Дракон " + dragon.getId() + " {\n" + dragon.toString() + "\n}")
            .collect(java.util.stream.Collectors.joining("\n"));
    }

    @Override
    public String clear() {
        dragons.clear();
        return "Коллекция очищена успешно!\n";
    }
    public String exit(){
        System.exit(0);
        return "Выход из программы";
    }
    public String max_by_weight() {
        return dragons.stream()
            .max((d1, d2) -> Long.compare(d1.getWeight(), d2.getWeight()))
            .map(dragon -> "Максимальный по весу дракон: " + dragon.toString())
            .orElse("Коллекция пуста");
    }

    public String head() {
        if (collection.getCollection() != null) {
            return "1-ый элемент коллекции {" + "\n" +
            collection.getCollection().getFirst().toString() +
            "}";
        } else {
            return ("Коллекция пуста");
        }
    }

    public String group_by() {
        return java.util.Arrays.stream(DragonType.values())
            .map(type -> {
                long count = dragons.stream()
                    .filter(dragon -> dragon.getType() == type)
                    .count();
                String dragons_of_type = dragons.stream()
                    .filter(dragon -> dragon.getType() == type)
                    .map(Dragon::toString)
                    .collect(java.util.stream.Collectors.joining("\n"));
                return String.format("Вывод драконов типа %s {\n%s\n}\nДраконов типа %s: %d\n",
                    type, dragons_of_type, type, count);
            })
            .collect(java.util.stream.Collectors.joining());
    }

    public String filter(String data) {
        try {
            Integer value = Integer.parseInt(data);
            String result = dragons.stream()
                .filter(dragon -> dragon.getCharacter() != null && dragon.getCharacter().ordinal() < value)
                .map(Dragon::toString)
                .collect(java.util.stream.Collectors.joining("\n"));
            return result.isEmpty() ? "Дракона с меньшим значением характера не найдено" : result;
        } catch (NumberFormatException e) {
            return "Параметр команды должен быть числом";
        }
    }

    public String remove_head() {
        if (collection.getData() != null) {
            Dragon dragon = collection.getCollection().getFirst();
            collection.remove(dragon);
            System.out.println("Успешно удалён");
            return ("1-ый элемент коллекции {") +
            (collection.getCollection().getFirst().toString()) +
            "}";
        } else {
            return ("Коллекция пуста, показывать и удалять нечего");
        }
    }

    public String remove_by_id(String data) {
        try {
            Integer id = Integer.parseInt(data);
            Dragon dragon = collection.search(id);
            if (dragon != null) {
                collection.remove(dragon);
                return ("Элемент с id = " + id + " успешно удалён!");
            }
        } catch (NumberFormatException e) {
            return ("ID должен быть целым числом");
        } catch (NullPointerException ignored) {
            return "";
        }
        return "";
    }

    @Override
    public Dragon search(int id) {
        return dragons.stream()
            .filter(dragon -> dragon.getId() == id)
            .findFirst()
            .orElseGet(() -> {
                System.out.println("Объекта с таким id не найдено в коллекции");
                return null;
            });
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

    public String update_id(Dragon dragon) throws NumberFormatException {
        Dragon olddragon = new Dragon();
        int m = 0;
        for(Dragon dragon1: collection.getCollection()){
            if(dragon1.getId() == dragon.getId()){
                olddragon = dragon1;
                m = -1;
            }
        }
        if(m == -1) {
            collection.remove(olddragon);
            collection.put(dragon);
            return ("Новый дракон успешно добавлен в коллекцию");
        }
        else{
            return "Дракона с таким id не найдено";
        }
    }


    public String add(Dragon dragon){
        collection.put(dragon);
        return ("Дракон успешно добавлен в коллекцию!");
    }
    @Override
    public String put(Dragon dragon){
        dragons.add(dragon);
        return "Dragon " + dragon.getName() + " добавлен в коллекцию";
    }
}
