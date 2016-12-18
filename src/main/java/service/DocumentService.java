package service;

import application.Util;
import pojo.Document;
import pojo.DocumentGroup;
import pojo.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by BB on 2016.05.22..
 */
public class DocumentService {
    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("mssql_pu");
    private static EntityManager em = emf.createEntityManager();
    private static EntityTransaction transaction = em.getTransaction();

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
        Util.begin(transaction);
        Document docToDb = new Document(document.getName(), document.getContent(), document.getOwner());
        List<DocumentGroup> groups = new ArrayList<>();
        for (DocumentGroup documentGroup : document.getContainingGroups()) {
            DocumentGroup ddb = em.find(DocumentGroup.class, documentGroup.getId());
            groups.add(ddb);
        }
        docToDb.setContainingGroups(groups);
        User udb = em.find(User.class, docToDb.getOwner().getId());
        docToDb.setOwner(udb);
        em.merge(docToDb);
        transaction.commit();
    }

    public void deleteDocumentById(String id) {
        Util.begin(transaction);
        String query = "delete from Document d where d.id=:id";
        em.createQuery(query).setParameter("id", Integer.valueOf(id)).executeUpdate();
        transaction.commit();
    }

    public static void closeAll() {
        em.close();
        emf.close();
    }

}
