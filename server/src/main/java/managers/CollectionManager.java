package managers;

import interfaces.Collectionable;
import managers.commands.Update_id;
import models.*;

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
    public String printInfo() {
        return ("Тип коллекции: " + dragons.getClass().getName() + "\nДата инициализации: " + localdatetime + "\nКоличество элементов: " + dragons.size());
    }

    @Override
    public String print() {
        if (dragons.size() == 0) {
            return ("Коллекция пуста");
        }
        String result = "";
        for (Dragon dragon : dragons) {
            result += ("Дракон " + dragon.getId() + " {\n") +
            (dragon.toString()) + "\n" +
            ("}");
        }
        return result;
    }

    @Override
    public String clear() {
        dragons.clear();
        return "Коллекция очищена успешно!\n";

    }
    public String exit(){
        save();
        System.exit(0);
        return "Выход из программы";
    }
    public String save(){
        Writer write = new Writer();
        write.writeCollection();
        return "Сохранено";
    }
    public String max_by_weight() {
        Dragon max_weight_dragon = collection.getCollection().getFirst();
        Long max_weight = collection.getCollection().getFirst().getWeight();
        for (Dragon dragon : collection.getData().getCollection()) {
            if (dragon.getWeight() > max_weight) {
                max_weight_dragon = dragon;
                max_weight = dragon.getWeight();
            }
        }
        return "Максимальный по весу дракон: " + (max_weight_dragon.toString());
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
        String finalresult = "";
        for (DragonType cnt : DragonType.values()) {
            Integer value = 0;
            String result = "";
            for (Dragon dragon : collection.getCollection()) {
                if (dragon.getType() == cnt) {
                    result += (dragon.toString()) + "\n";
                    value++;
                }
            }
            finalresult += "Вывод драконов типа " + cnt.toString() + " {\n" + result + "}\nДраконов типа " + cnt.toString() + ": " + value + "\n";
        }
        return finalresult;
    }

    public String filter(String data) {
        boolean found = false;
        try {
            String result = "";
            Integer value = Integer.parseInt(data);
            for (Dragon dragon : collection.getCollection()) {
                if (dragon.getCharacter() != null && dragon.getCharacter().ordinal() < value) {
                    result += (dragon.toString()) + "\n";
                    found = true;
                }
            }
            if (!found) {
                return("Дракона с меньшим значением характера не найдено");
            }
            return result;
        } catch (NumberFormatException e) {return ("Параметр команды должен быть числом");
        }
    }

    public String remove_head() {
        if (collection.getData() != null) {
            Dragon dragon = collection.getCollection().getFirst();
            collection.remove(dragon);
            return ("1-ый элемент коллекции {") +
            (collection.getCollection().getFirst().toString()) +
            "} Успешно удалён";
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
