package domain;


import javax.persistence.*;
import java.security.Principal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "user")
public class User implements Principal {

    @Id
    @Column
    @GeneratedValue
    private long id;

    @Column
    private String name;

    @Column(nullable = false)
    private String password;

    @Column
    private Date lastVisit;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
    private transient Set<User> favorites;

    private User() {
        this.favorites = new HashSet<>();
    }

    public User(String name) {
        this();
        this.name = name;
    }

    public User(String name, String password) {
        this(name);
        this.password = password;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<User> getFavorites() {
        return favorites;
    }

    public void setFavorites(Set<User> favorites) {
        this.favorites = favorites;
    }

    public Date getLastVisit() {
        return lastVisit;
    }

    public void setLastVisit(Date lastVisit) {
        this.lastVisit = lastVisit;
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
