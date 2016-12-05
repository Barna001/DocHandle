package service;

import application.Util;
import pojo.DocumentGroup;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

/**
 * Created by Barna on 2016.05.26..
 */
public class DocumentGroupService {

    private static EntityManagerFactory emf = Util.getFactory();
    private static EntityManager em = emf.createEntityManager();
    private static EntityTransaction transaction = em.getTransaction();

    public static DocumentGroup getDocumentGroupById(String id) {
        return em.find(DocumentGroup.class, id);
    }

    public static List<DocumentGroup> getDocumentGroupByName(String name) {
        String query = "select g from DocumentGroup g where g.name=:name";
        return em.createQuery(query, DocumentGroup.class).setParameter("name", name).getResultList();
    }

    public static List<DocumentGroup> getAllDocumentGroups() {
        String query = "select g from DocumentGroup g";
        return em.createQuery(query, DocumentGroup.class).getResultList();
    }

    public static void addGroup(DocumentGroup group) {
        transaction.begin();
        em.persist(group);
        transaction.commit();
    }

    public static void deleteGroup(String id) {
        transaction.begin();
        String query = "delete from DocumentGroup dg where dg.id=:id";
        em.createQuery(query).setParameter("id", id).executeUpdate();
        transaction.commit();
    }

    public static void deleteAll() {
        transaction.begin();
        String query = "delete from DocumentGroup";
        em.createQuery(query).executeUpdate();
        transaction.commit();
    }

    public static void closeAll() {
        em.close();
        emf.close();
    }
}
