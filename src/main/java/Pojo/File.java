package Pojo;

import javax.persistence.*;
import java.util.*;

@Entity
public class File {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rootDocument", nullable = false)
    private Document rootDocument;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "rootFile", fetch = FetchType.LAZY)
    private Map<Integer, FileVersion> versionsMap = new HashMap<>();

    public File() {
    }

    public File(String name, Document root) {
        this.name = name;
        this.rootDocument = root;
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

    public Map<Integer, FileVersion> getVersionsMap() {
        return versionsMap;
    }

    public void setVersionsMap(Map<Integer, FileVersion> versionsMap) {
        this.versionsMap = versionsMap;
    }

    public FileVersion getLatestVersion() {
        return this.versionsMap.get(Collections.max(this.versionsMap.keySet()));
    }
}
