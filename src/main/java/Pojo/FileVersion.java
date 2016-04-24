package Pojo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

@org.springframework.data.mongodb.core.mapping.Document
public class FileVersion {
    @Id
    private String id;
    private int versionNumber;
    private byte data;
    @DBRef
    private File rootFile;

    public FileVersion() {
    }

    public FileVersion(int versionNumber, byte data, File rootFile) {
        this.versionNumber = versionNumber;
        this.data = data;
        this.rootFile = rootFile;
    }
}
