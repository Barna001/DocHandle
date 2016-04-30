package Pojo;

import org.eclipse.persistence.jpa.jpql.parser.DateTime;

import javax.persistence.*;
import javax.persistence.criteria.FetchParent;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;
    private String name;
    private String content;
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;
    @Temporal(TemporalType.TIMESTAMP)
    private Date modificationDate;

    @ManyToOne(fetch = FetchType.LAZY)
    private User owner;

    @ManyToMany(mappedBy = "documents")
    private List<DocumentGroup> containingGroups;

    public Document() {
        this.containingGroups = new ArrayList<>();
    }

    public Document(String name, String content, User owner) {
        this.owner = owner;
        this.name = name;
        this.content = content;
        this.creationDate = new Date();
        this.modificationDate = new Date();
        this.containingGroups = new ArrayList<>();
    }

    @Override
    public String toString(){
        return "Id:"+id+" name:"+name;
    }
}
