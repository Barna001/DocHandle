package pojo;

import javax.persistence.*;

@Entity
public class File {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rootDocument",nullable = false)
    private Document rootDocument;

    private int latestVersionNumber;
    private String latestVersionId;


    public File() {
    }

    public File(String name, Document root) {
        this.name = name;
        this.rootDocument = root;
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
    }

}
