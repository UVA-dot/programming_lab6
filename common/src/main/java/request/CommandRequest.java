package request;

import models.Dragon;

public class CommandRequest extends Request {
    public long id;
    public String line;
    public Dragon dragon;
    public CommandRequest(String name) {
        super(name);
    }
    public CommandRequest(String name, long id){
        super(name);
        this.id=id;
    }
    public CommandRequest(String name, long id, Dragon dragon){
        super(name);
        this.id=id;
        this.dragon=dragon;
    }
    public CommandRequest(String name, String line){
        super(name);
        this.line=line;
    }

    public long getId() {
        return id;
    }

    public String getLine() {
        return line;
    }

    public Dragon getDragon() {
        return dragon;
    }
}
