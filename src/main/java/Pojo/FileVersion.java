package pojo;

import javax.persistence.*;

@Entity
public class FileVersion {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @Lob
    private byte[] data;

    private String rootFileId;

    private int versionNumber;

    private String fileType;

    public FileVersion() {
    }

    public FileVersion(String rootFileId, byte[] data) {
        this.data = data;
        this.rootFileId = rootFileId;
    }

    public String getRootFileId() {
        return rootFileId;
    }

    public void setRootFileId(String rootFileId) {
        this.rootFileId = rootFileId;
    }

    public int getVersionNumber() {
        return versionNumber;
    }

    public void setVersionNumber(int versionNumber) {
        this.versionNumber = versionNumber;
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

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }
}
