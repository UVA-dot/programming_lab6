package response;

public class CommandResponse extends Response{
    public String line;
    public CommandResponse(String name, String error, String result) {
        super(name, error, result);
    }

}