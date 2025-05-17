package server.managers.commands;

import server.managers.commands.Command;

public class Exit extends AbstractCommand {
    public Exit(){
        super("Exit", "Выйти из приложения без сохранения коллекции в файл");
    }
}
