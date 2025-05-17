package managers;

import interfaces.Collectionable;
import managers.commands.Update_id;
import models.*;
import request.CommandRequest;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.Scanner;

public class CollectionManager implements Collectionable {
    private static final LocalDateTime localdatetime;
    public static CommandRequest request;
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
        request = new CommandRequest("");
        return add();
    }


    public String add(){
        request = new CommandRequest("");
        return null;
    }
}
