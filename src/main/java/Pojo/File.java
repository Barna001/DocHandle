package Pojo;

import com.impetus.kundera.index.IndexingException;

import javax.persistence.*;
import java.util.*;

@Entity
public class File {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rootDocument")
    private Document rootDocument;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "rootFile",fetch = FetchType.LAZY)
    private List<FileVersion> versionsList=new ArrayList<>();

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

    public int getLatestNumber(){
        return versionsList.size()-1;
    }

    public FileVersion getLatestVersion() {
        return versionsList.get(getLatestNumber());
    }

    public FileVersion getFileVersionByNumber(int versionNumber) {
        if (versionsList.size()>versionNumber) {
            return versionsList.get(versionNumber);
        } else {
            throw new IndexingException("No such version exists from the file");
        }
    }

    public List<FileVersion> getVersionsList() {
        return versionsList;
    }

    public void setVersionsList(List<FileVersion> versionsList) {
        this.versionsList = versionsList;
    }
}
