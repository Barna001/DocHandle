package Pojo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


public class Document {
    private String id;
    private String name;
    private String content;
    private LocalDateTime creationDate;
    private LocalDateTime modificationDate;
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
