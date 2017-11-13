package domain;

/**
 * Created by User on 01.11.2017.
 */
public class ServiceMessage {

    private Type type;

    private String content;

    public ServiceMessage(Type type, String content) {
        this.type = type;
        this.content = content;
    }

    public enum Type {
        LOGGED_IN,
        LOGGED_OUT,
        BANNED,
        KICKED,
        WARNING,
        SYSTEM_EVENT
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
