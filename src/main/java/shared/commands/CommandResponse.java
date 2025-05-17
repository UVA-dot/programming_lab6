package shared.commands;

import java.io.Serializable;

public class CommandResponse implements Serializable {
    private final String response;

    public CommandResponse(String response) {
        this.response = response;
    }

    public String getResponse() {
        return response;
    }
}