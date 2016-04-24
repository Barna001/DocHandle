package Pojo;

import org.springframework.data.mongodb.core.mapping.DBRef;

import java.util.List;

public class File {
    private String name;
    @DBRef
    private Document rootDocument;

    public File() {
    }

    public File(String name, Document root) {
        this.name = name;
        this.rootDocument=root;
    }
}
