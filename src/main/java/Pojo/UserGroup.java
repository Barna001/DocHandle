package Pojo;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
@DiscriminatorValue("G")
public class UserGroup extends PermissionSubject {

    @JoinTable(name="group_contains_user",
        joinColumns = @JoinColumn(name="group_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name="user_id", referencedColumnName = "id")
    )
    @ManyToMany(cascade = CascadeType.ALL)
    private List<User> users;

    public UserGroup() {
        this.users = new ArrayList<>();
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    @Override
    public String toString(){
        return "Id:" + id + " name:" + name;
    }
}
