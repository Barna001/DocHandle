package pojo;

import org.codehaus.jackson.annotate.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
//@Table
@DiscriminatorValue("USER")
public class User extends PermissionSubject {

    @OneToMany(cascade = {CascadeType.MERGE}, mappedBy = "owner", fetch = FetchType.LAZY)
    private List<Document> ownDocuments = new ArrayList<Document>();

    private UserRoleEnum role;

    @JoinTable(name = "group_contains_user",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "group_id", referencedColumnName = "id"))
    @ManyToMany(cascade = {CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<UserGroup> groups = new ArrayList<UserGroup>();

    @Transient
    private String groupNames = "";

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
        return Collections.unmodifiableList(this.groups);
    }

    public void setGroups(List<UserGroup> groups) {
        this.groups = groups;
        for (UserGroup group : groups) {
            if (this.groupNames.isEmpty()) {
                this.groupNames = group.getName();
            } else {
                this.groupNames += "," + group.getName();
            }
        }
    }

    public List<Document> getOwnDocuments() {
        return ownDocuments;
    }

    public void setOwnDocuments(List<Document> ownDocuments) {
        this.ownDocuments = ownDocuments;
    }

    //To prevent unnecessary duplicated information storage in db, calculated on Object level
    public String getGroupNames() {
        groupNames = "";
        for (UserGroup group : groups) {
            if (this.groupNames.isEmpty()) {
                this.groupNames = group.getName();
            } else {
                this.groupNames += "," + group.getName();
            }
        }
        return groupNames;
    }

}
