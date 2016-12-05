package pojo;

import org.codehaus.jackson.annotate.JsonIgnore;

import javax.persistence.*;

//todo xml rootelement
@Entity
public class File {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;

    private String name;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "rootDocument", nullable = false)
    @JsonIgnore
    //todo xml transient
    private Document rootDocument;

    @Transient
    private String rootDocumentName;

    private int latestVersionNumber;
    private String latestVersionId;


    public File() {
    }

    public File(String name, Document root) {
        this.name = name;
        this.rootDocument = root;
        this.rootDocumentName = root.getName();
    }

    public String getLatestVersionId() {
        return latestVersionId;
    }

    public void setLatestVersionId(String latestVersionId) {
        this.latestVersionId = latestVersionId;
    }

    public int getLatestVersionNumber() {
        return latestVersionNumber;
    }

    public void setLatestVersionNumber(int latestVersionNumber) {
        this.latestVersionNumber = latestVersionNumber;
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

    public Document getRootDocument() {
        return rootDocument;
    }

    public void setRootDocument(Document rootDocument) {
        this.rootDocument = rootDocument;
        this.rootDocumentName = rootDocument.getName();
    }
    public String getRootDocumentName() {
        return rootDocument.getName();
    }

    public void setRootDocumentName(String rootDocumentName) {
        this.rootDocumentName = rootDocumentName; this.rootDocument.setName(rootDocumentName);
    }
}
