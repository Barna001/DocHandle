package pojo;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
@DiscriminatorValue("USER")
public class User extends PermissionSubject {

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH}, mappedBy = "owner", fetch = FetchType.LAZY)
    private List<Document> ownDocuments = new ArrayList<Document>();

    private UserRoleEnum role;

    @JoinTable(name = "group_contains_user",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "group_id", referencedColumnName = "id"))
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
    private List<UserGroup> groups = new ArrayList<UserGroup>();

    public User() {
    }

    public User(String name, UserRoleEnum role) {
        this.name = name;
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

    public List<Document> getOwnDocuments() {
        return ownDocuments;
    }

    public void setOwnDocuments(List<Document> ownDocuments) {
        this.ownDocuments = ownDocuments;
    }

    @Override
    public String toString() {
        return "Id:" + id + " name:" + name + " role:" + role;
    }
}
