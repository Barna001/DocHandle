package pojo;

import javax.management.InvalidAttributeValueException;
import javax.persistence.*;

@Entity
public class GroupAccess {
    @Transient
    public static final int minimumPriority = 1;
    @Transient
    public static final int maximumPriority = 10000;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "who")
    private PermissionSubject who;//todo index

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "what")
    private DocumentGroup what;

    private GroupAccessTypeEnum type;

    int priority;

    public GroupAccess() {
    }

    public GroupAccess(PermissionSubject who, DocumentGroup what, GroupAccessTypeEnum type, int priority) throws InvalidAttributeValueException {
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

    public DocumentGroup getWhat() {
        return what;
    }

    public void setWhat(DocumentGroup what) {
        this.what = what;
    }

    public GroupAccessTypeEnum getType() {
        return type;
    }

    public void setType(GroupAccessTypeEnum type) {
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
