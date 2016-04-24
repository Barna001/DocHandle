package Pojo;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document
public class UserGroup extends PermissionSubject {

    @DBRef
    private List<User> users;

    public UserGroup() {
        this.users = new ArrayList<>();
    }

    public void addUser(User user){
        this.users.add(user);
        user.addGroup(this);
    }

}
