package service;

import application.Util;
import pojo.User;
import pojo.UserGroup;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

/**
 * Created by BB on 2016.05.22..
 */
public class UserGroupService {

    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("mongo_pu");
    private static EntityManager em = emf.createEntityManager();
    private static EntityTransaction transaction = em.getTransaction();

    public static UserGroup getUserGroupById(String id) {
        return em.find(UserGroup.class, id);
    }

    public static List<UserGroup> getUserGroupByName(String name) {
        String query = "select g from UserGroup g where g.name=:name";
        return em.createQuery(query, UserGroup.class).setParameter("name", name).getResultList();
    }

    public static List<UserGroup> getAllUserGroups() {
        String query = "select g from UserGroup g";
        return em.createQuery(query, UserGroup.class).getResultList();
    }

    public static void addGroup(UserGroup group) {
        Util.begin(transaction);
        em.persist(group);
        transaction.commit();
    }

    public static void deleteGroup(String id) {
        Util.begin(transaction);
        String query = "delete from UserGroup ug where ug.id=:id";
        em.createQuery(query).setParameter("id", id).executeUpdate();
        transaction.commit();
    }

    public static void closeAll() {
        em.close();
        emf.close();
    }

}
