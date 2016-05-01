package Pojo;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name="Subject_type")
@Table
public abstract class PermissionSubject {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected String id;
    protected String name;

    public PermissionSubject(){
    }

    public PermissionSubject(String name){
        this.name=name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
