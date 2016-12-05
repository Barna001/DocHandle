package service;


import application.Util;
import pojo.Document;
import pojo.User;
import pojo.UserGroup;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.ArrayList;
import java.util.List;

public class UserService {

    private static EntityManagerFactory emf = Util.getFactory();
    private static EntityManager em = emf.createEntityManager();
    private static EntityTransaction transaction = em.getTransaction();

    public static User getDefaultUser() {
        User user = new User();
        user.setName("Barna");
        return user;
    }

    public static User getUserById(String id) {
        return em.find(User.class, id);
    }

    public static List<User> getUserByName(String name) {
        String query = "select u from User u where u.name=:name";
        return em.createQuery(query, User.class).setParameter("name", name).getResultList();
    }

    public static List<User> getAllUsers() {
        String query = "select u from User u";
        return em.createQuery(query, User.class).getResultList();
    }

    public static void addUser(User user) {
        transaction.begin();
        User userToDb = new User(user.getName(), user.getRole());
        List<UserGroup> groups = new ArrayList<>();
        for (UserGroup userGroup : user.getGroups()) {
            UserGroup udb=em.find(UserGroup.class, userGroup.getId());
            groups.add(udb);
        }
        userToDb.setGroups(groups);
        em.merge(userToDb);
        transaction.commit();
    }

    public static void deleteUser(String userId) {
        transaction.begin();
        String query = "delete from User u where u.id=:id";
        em.createQuery(query).setParameter("id", userId).executeUpdate();
        transaction.commit();
    }

    public static void deleteAll() {
        transaction.begin();
        String query = "delete from User";
        em.createQuery(query).executeUpdate();
        transaction.commit();
    }

    public static void closeAll() {
        em.close();
        emf.close();
    }

}
