package Pojo;

import javax.persistence.*;

@Entity
public class FileVersion {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;

    @Lob
    private byte[] data;

    @ManyToOne
    @JoinColumn(name="rootFile")
    private File rootFile;

    private String rootFileId;

    public String getRootFileId() {
        return rootFileId;
    }

    public void setRootFileId(String rootFileId) {
        this.rootFileId = rootFileId;
    }

    private int versionNumber;

    public int getVersionNumber() {
        return versionNumber;
    }

    public void setVersionNumber(int versionNumber) {
        this.versionNumber = versionNumber;
    }

    public FileVersion() {
    }

    public FileVersion(byte[] data,File rootFile) {
        this.data = data;
        this.rootFile=rootFile;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public File getRootFile() {
        return rootFile;
    }

    public void setRootFile(File rootFile) {
        this.rootFile = rootFile;
    }
}
