package Pojo;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
@DiscriminatorValue("U")
public class User extends PermissionSubject {

    @OneToMany(mappedBy="owner")
    private List<Document> ownDocuments;
    private UserRoleEnum role;

    @JoinTable(name = "group_contains_user",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "group_id", referencedColumnName = "id"))
    @ManyToMany(cascade = CascadeType.ALL)
    private List<UserGroup> groups=new ArrayList<>();

    public User() {
    }

    public User(String name, UserRoleEnum role) {
        this.name=name;
        this.role = role;
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
