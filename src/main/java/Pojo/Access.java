package pojo;

import javax.management.InvalidAttributeValueException;
import javax.persistence.*;

@Entity
public class Access {

    public static final int minimumPriority = 1;
    public static final int maximumPriority = 10000;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    private PermissionSubject who;//todo index

    @ManyToOne(fetch = FetchType.LAZY)
    private pojo.Document what;

    private AccessTypeEnum type;
    private int priority;

    public Access() {
    }

    public Access(PermissionSubject who, Document what, AccessTypeEnum type, int priority) throws InvalidAttributeValueException {
        this.who = who;
        this.what = what;
        this.type = type;
        if (priority < minimumPriority || priority > maximumPriority) {
            throw new InvalidAttributeValueException("The priority must be at least 1 and maximum 10.000");
        } else {
            this.priority = priority;
        }
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

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) throws InvalidAttributeValueException {
        if (priority < minimumPriority || priority > maximumPriority) {
            throw new InvalidAttributeValueException("The priority must be at least 1 and maximum 10.000");
        } else {
            this.priority = priority;
        }
    }

}
