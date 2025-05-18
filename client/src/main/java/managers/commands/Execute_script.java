package managers.commands;

import models.Dragon;

public class Execute_script extends Command{
    public Execute_script(){
        super("Execute_script", "считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме");

    }
    @Override
    public String execute(String arg){
        collectionManager.execute_script(arg);
        return null;
    }
}
