package pojo;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class DocumentGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;
    private String name;
    private String description;

    @JoinTable(name = "group_contains_document",
            joinColumns = @JoinColumn(name = "group_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "document_id", referencedColumnName = "id"))
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    private List<Document> documents;

    public DocumentGroup() {
        this.documents = new ArrayList<Document>();
    }

    public DocumentGroup(String name, String description) {
        this.name = name;
        this.description = description;
        this.documents = new ArrayList<Document>();
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Document> getDocuments() {
        return documents;
    }

    public void setDocuments(List<Document> documents) {
        this.documents = documents;
    }
}
