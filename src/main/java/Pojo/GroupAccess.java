package Pojo;

public class GroupAccess {
    private String id;
    private PermissionSubject who;//todo index
    private DocumentGroup what;
    private AccessTypeEnum type;
    long priority;

    public GroupAccess() {
    }

    public GroupAccess(PermissionSubject who, DocumentGroup what, AccessTypeEnum type, long priority) {
        this.who = who;
        this.what = what;
        this.type = type;
        this.priority=priority;
    }
}
