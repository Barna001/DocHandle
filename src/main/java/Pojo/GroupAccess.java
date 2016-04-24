package Pojo;

import org.springframework.data.annotation.Id;

@org.springframework.data.mongodb.core.mapping.Document
public class GroupAccess {
    @Id
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
