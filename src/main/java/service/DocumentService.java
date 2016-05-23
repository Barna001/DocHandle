package service;

import pojo.Document;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
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
        em.persist(document);
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
