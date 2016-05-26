package pojo;

import org.codehaus.jackson.annotate.JsonIgnore;

import javax.persistence.*;
import java.util.*;

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

    //todo implement json construction
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH}, fetch = FetchType.LAZY)
    @JoinColumn(name = "owner")
    @JsonIgnore
    private User owner;

    @JoinTable(name = "group_contains_document",
            joinColumns = @JoinColumn(name = "document_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "group_id", referencedColumnName = "id"))
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.REFRESH, CascadeType.MERGE})
    private List<DocumentGroup> containingGroups = new ArrayList<DocumentGroup>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "rootDocument", fetch = FetchType.LAZY)
    private List<File> files = new ArrayList<File>();

    public Document() {
        this.creationDate = new Date();
        this.modificationDate = this.creationDate;
    }

    public Document(String name, String content, User owner) {
        this.owner = owner;
        this.name = name;
        this.content = content;
        this.creationDate = new Date();
        this.modificationDate = this.creationDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
        this.modificationDate = new Date();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        this.modificationDate = new Date();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
        this.modificationDate = new Date();
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public Date getModificationDate() {
        return modificationDate;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
        this.modificationDate = new Date();
    }

    //we return an unmodifiableList to prevent changes without updating the modification date
    public List<DocumentGroup> getContainingGroups() {
        return Collections.unmodifiableList(containingGroups);
    }

    //methods below is created because this way we can maintain modification date
    //(JPA does not allows any other type than Collection, List... etc. so no wrapper class or observablecollections allowed)
    public boolean addDocumentGroup(DocumentGroup group) {
        this.modificationDate = new Date();
        return this.containingGroups.add(group);
    }

    public boolean removeDocumentGroup(DocumentGroup group) {
        this.modificationDate = new Date();
        return this.containingGroups.remove(group);
    }

    public void setContainingGroups(List<DocumentGroup> containingGroups) {
        this.containingGroups = containingGroups;
        this.modificationDate = new Date();
    }

    @Override
    public String toString() {
        return "Id:" + id + " name:" + name;
    }

}
