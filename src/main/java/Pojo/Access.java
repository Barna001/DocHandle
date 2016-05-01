package Pojo;

import javax.persistence.*;

@Entity
public class Access {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    private PermissionSubject who;//todo index

    @ManyToOne(fetch = FetchType.LAZY)
    private Pojo.Document what;
    private AccessTypeEnum type;
    private long priority;

    public Access() {
    }

    public Access(PermissionSubject who, Pojo.Document what, AccessTypeEnum type, long priority) {
        this.who = who;
        this.what = what;
        this.type = type;
        this.priority = priority;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public PermissionSubject getWho() {
        return who;
    }

    public void setWho(PermissionSubject who) {
        this.who = who;
    }

    public Document getWhat() {
        return what;
    }

    public void setWhat(Document what) {
        this.what = what;
    }

    public AccessTypeEnum getType() {
        return type;
    }

    public void setType(AccessTypeEnum type) {
        this.type = type;
    }

    public long getPriority() {
        return priority;
    }

    public void setPriority(long priority) {
        this.priority = priority;
    }

}
