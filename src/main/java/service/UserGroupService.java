package service;

import pojo.User;
import pojo.UserGroup;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

/**
 * Created by BB on 2016.05.22..
 */
public class UserGroupService {

    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("mongo_pu");
    private static EntityManager em = emf.createEntityManager();

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
        em.persist(group);
    }

    public static void deleteAll() {
        String query = "delete from UserGroup";
        em.createQuery(query).executeUpdate();
    }

    public static void closeAll() {
        em.close();
        emf.close();
    }
}
