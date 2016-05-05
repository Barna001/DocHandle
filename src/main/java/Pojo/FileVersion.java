package Pojo;

import javax.persistence.*;

@Entity
public class FileVersion {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;
    private int versionNumber;

    @Lob
    private byte[] data;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rootFile", nullable = false)
    private File rootFile;

    public FileVersion() {
    }

    public FileVersion(int versionNumber, byte[] data, File rootFile) {
        this.versionNumber = versionNumber;
        this.data = data;
        this.rootFile = rootFile;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getVersionNumber() {
        return versionNumber;
    }

    public void setVersionNumber(int versionNumber) {
        this.versionNumber = versionNumber;
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
