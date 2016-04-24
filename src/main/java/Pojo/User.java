package Pojo;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by BB on 2016.04.24..
 */
@Document
public class User extends PermissionSubject{
    private UserRoleEnum role;
    private List<UserGroup> groups;

    public User() {
        this.groups=new ArrayList<>();
    }

    public User(String name, UserRoleEnum role){
        super(name);
        this.role=role;
        this.groups=new ArrayList<>();
    }

    protected void addGroup(UserGroup group){
        this.groups.add(group);
    }
}
