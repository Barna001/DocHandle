package Pojo;

public class GroupAccess {
    private String id;
    private PermissionSubject who;//todo index
    private DocumentGroup what;
    private AccessTypeEnum type;

    public GroupAccess() {
    }

    public GroupAccess(PermissionSubject who, DocumentGroup what, AccessTypeEnum type) {
        this.who = who;
        this.what = what;
        this.type = type;
    }
}
