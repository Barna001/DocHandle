package Pojo;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
@DiscriminatorValue("U")
public class User extends PermissionSubject {

    private UserRoleEnum role;

    @ManyToMany(mappedBy = "users")
    private List<UserGroup> groups;

    public User() {
        this.groups = new ArrayList<>();
    }

    public User(String name, UserRoleEnum role) {
        this.name=name;
        this.role = role;
        this.groups = new ArrayList<>();
    }

    public UserRoleEnum getRole() {
        return role;
    }

    public void setRole(UserRoleEnum role) {
        this.role = role;
    }

    public List<UserGroup> getGroups() {
        return groups;
    }

    public void setGroups(List<UserGroup> groups) {
        this.groups = groups;
    }

    @Override
    public String toString() {
        return "Id:" + id + " name:" + name + " role:" + role;
    }
}
