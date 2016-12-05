package pojo;

import javax.persistence.*;

@Entity
public class FileVersion {
    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Lob
    private byte[] data;

    private int rootFileId;

    private int versionNumber;

    public FileVersion() {
    }

    public FileVersion(int rootFileId, byte[] data) {
        this.data = data;
        this.rootFileId = rootFileId;
    }

    public int getRootFileId() {
        return rootFileId;
    }

    public void setRootFileId(int rootFileId) {
        this.rootFileId = rootFileId;
    }

    public int getVersionNumber() {
        return versionNumber;
    }

    public void setVersionNumber(int versionNumber) {
        this.versionNumber = versionNumber;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }
}
