package Pojo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@org.springframework.data.mongodb.core.mapping.Document
public class Document {
    @Id
    private String id;
    private String name;
    private String content;
    private LocalDateTime creationDate;
    private LocalDateTime modificationDate;
    @DBRef
    private User owner;
    private List<DocumentGroup> containingGroups;

    public Document() {
        this.containingGroups = new ArrayList<>();
    }

    public Document(String name, String content, User owner) {
        this.owner = owner;
        this.name = name;
        this.content = content;
        this.creationDate = LocalDateTime.now();
        this.modificationDate = LocalDateTime.now();
        this.containingGroups = new ArrayList<>();
    }

    //only the DocumentGroup class addDocument method can call this method!
    protected void addGroup(DocumentGroup group) {
        this.containingGroups.add(group);
    }



}
