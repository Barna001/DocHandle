package pojo;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
@DiscriminatorValue("GROUP")
public class UserGroup extends PermissionSubject {

    @JoinTable(name = "group_contains_user",
            joinColumns = @JoinColumn(name = "group_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"))
    @ManyToMany(cascade = CascadeType.ALL)
    private List<User> users = new ArrayList<User>();

    public UserGroup() {
    }

    public UserGroup(String name) {
        this.name = name;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("\t Users in group:\t");
        for (User user : users) {
            sb.append("\t\t" + user.toString() + "\n");
        }
        return "Id:" + id + " name:" + name + "\n" +
                sb.toString();
    }
}
