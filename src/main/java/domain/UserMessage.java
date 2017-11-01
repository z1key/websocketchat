package domain;

import java.io.Serializable;

/**
 * Created by UserMessage on 29.10.2017.
 */
public class UserMessage implements Serializable {

    private String name;

    public UserMessage() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserMessage)) return false;

        UserMessage user = (UserMessage) o;

        return name != null ? name.equals(user.name) : user.name == null;
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }
}
