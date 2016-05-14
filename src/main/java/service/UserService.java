package service;

import pojo.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

/**
 * Created by Barna on 2016.05.13..
 */
public final class UserService {
    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("mongo_pu");
    private static EntityManager em=emf.createEntityManager();

    private UserService(){
    }

    public User getUserById(String id){
        return em.find(User.class,id);
    }

    public List<User> getAllUsers(){
        return em.createQuery("select u from User u",User.class).getResultList();
    }
}
