package Pojo;

import java.util.ArrayList;
import java.util.List;

public class DocumentGroup {


    private String id;
    private String name;
    private String description;
    private List<Document> documents;

    public DocumentGroup() {
        this.documents=new ArrayList<>();
    }

    public DocumentGroup(String name, String description) {
        this.name = name;
        this.description = description;
        this.documents=new ArrayList<>();
    }

    public void addDocument(Document doc){
        this.documents.add(doc);
        doc.addGroup(this);
    }

}
