package Pojo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

public class PermissionSubject {
    @Id
    private String id;
    private String name;

    public PermissionSubject(){
    }

    public PermissionSubject(String name){
        this.name=name;
    }

}
