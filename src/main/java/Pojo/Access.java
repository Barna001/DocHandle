package Pojo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Access {
    @Id
    private String id;
    private PermissionSubject who;//todo index
    private Pojo.Document what;
    private AccessTypeEnum type;

    public Access() {
    }

    public Access(PermissionSubject who, Pojo.Document what, AccessTypeEnum type) {
        this.who = who;
        this.what = what;
        this.type =type;
    }
}
