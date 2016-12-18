package pojo;

import javax.persistence.*;

@Entity
public class FileVersion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Lob
    private byte[] data;

    private int rootFileId;

    private int versionNumber;

    private String fileType;

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

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }
}
