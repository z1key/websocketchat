package domain;


import javax.persistence.*;
import java.security.Principal;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "user")
public class User implements Principal {

    @Id
    private long id;

    @Column
    private String name;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
    private transient Set<User> favorites;

    private User() {
        this.favorites = new HashSet<>();
    }

    public User(String name) {
        this();
        this.name = name;
    }

    public User(Principal principal) {
        this();
        this.name = principal.getName();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<User> getFavorites() {
        return favorites;
    }

    public void setFavorites(Set<User> favorites) {
        this.favorites = favorites;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;

        User user = (User) o;

        if (id != user.id) return false;
        return name.equals(user.name);
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + name.hashCode();
        return result;
    }
}
