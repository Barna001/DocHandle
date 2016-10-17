package service;


import pojo.Document;
import pojo.User;
import pojo.UserGroup;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class UserService {

    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("mongo_pu");
    private static EntityManager em = emf.createEntityManager();

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
        em.persist(user);
    }

    public static void deleteUser(String userId) {
        String query = "delete from User u where u.id=:id";
        em.createQuery(query).setParameter("id", userId).executeUpdate();
    }

    public static void deleteAll() {
        String query = "delete from User";
        em.createQuery(query).executeUpdate();
    }

    public static void closeAll() {
        em.close();
        emf.close();
    }

}
