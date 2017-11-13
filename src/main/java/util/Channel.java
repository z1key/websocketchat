package util;

/**
 * Created by User on 14.11.2017.
 */
public enum Channel {
    SYSTEM_EVENT("/system/event"),
    SYSTEM_ERROR("/user/queue/errors"),
    TOPIC_ALL("/topic/all");

    private String value;

    Channel(String channel) {
        this.value = channel;
    }

    public String value() {
        return value;
    }
}
