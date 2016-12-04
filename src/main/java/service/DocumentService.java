package service;

import pojo.Document;
import pojo.DocumentGroup;
import pojo.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by BB on 2016.05.22..
 */
public class DocumentService {
    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("mongo_pu");
    private static EntityManager em = emf.createEntityManager();

    public static Document getDocumentById(String id) {
        return em.find(Document.class, id);
    }

    public static List<Document> getDocumentByName(String name) {
        String query = "select d from Document d where d.name=:name";
        return em.createQuery(query, Document.class).setParameter("name", name).getResultList();
    }

    public static List<Document> getAllDocuments() {
        String query = "select d from Document d";
        return em.createQuery(query, Document.class).getResultList();
    }

    public static void addDocument(Document document) {
        Document docToDb = new Document(document.getName(),document.getContent(),document.getOwner());
        List<DocumentGroup> groups = new ArrayList<>();
        for (DocumentGroup documentGroup : document.getContainingGroups()) {
            DocumentGroup ddb=em.find(DocumentGroup.class, documentGroup.getId());
            groups.add(ddb);
        }
        docToDb.setContainingGroups(groups);
        User udb = em.find(User.class, docToDb.getOwner().getId());
        docToDb.setOwner(udb);
        em.merge(docToDb);
    }

    public void deleteDocumentById(String id) {
        String query = "delete from Document d where d.id=:id";
        em.createQuery(query).setParameter("id", id).executeUpdate();
    }

    public static void deleteAll() {
        String query = "delete from Document";
        em.createQuery(query).executeUpdate();
    }

    public static void closeAll() {
        em.close();
        emf.close();
    }

}
