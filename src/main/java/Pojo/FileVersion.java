package Pojo;

public class FileVersion {
    private String id;
    private int versionNumber;
    private byte data;
    private File rootFile;

    public FileVersion() {
    }

    public FileVersion(int versionNumber, byte data, File rootFile) {
        this.versionNumber = versionNumber;
        this.data = data;
        this.rootFile = rootFile;
    }
}
