package Pojo;

public class File {
    private String name;
    private Document rootDocument;

    public File() {
    }

    public File(String name, Document root) {
        this.name = name;
        this.rootDocument=root;
    }
}
