package request;

import java.io.Serializable;

public abstract class Request implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private final String name;
    public Request(String name){
        this.name=name;
    }

    public String getName() {
        return name;
    }

}