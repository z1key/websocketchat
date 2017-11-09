package domain;

/**
 * Created by UserMessage on 29.10.2017.
 */
public class Message {

    private String sender;

    private String[] receivers;

    private String content;

    public Message() {
    }

    /**
     * For message controller
     */
    public Message(String[] receivers, String content) {
        this.receivers = receivers;
        this.content = content;
    }

    public Message(String sender, String content) {
        this.sender = sender;
        this.content = content;
    }

    public Message(String sender, String[] receivers, String content) {
        this.sender = sender;
        this.receivers = receivers;
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String[] getReceivers() {
        return receivers;
    }

    public void setReceivers(String[] receivers) {
        this.receivers = receivers;
    }
}
