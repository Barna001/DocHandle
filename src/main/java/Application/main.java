package Application;

import Pojo.User;
import Pojo.UserGroup;
import Pojo.UserRoleEnum;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.sound.midi.SysexMessage;
import java.io.IOException;

/**
 * Created by Barna on 2016.04.28..
 */
public class main {
    public static void main(String[] args) throws IOException {
        User me = new User("Barna", UserRoleEnum.SUPER_ADMIN);
        UserGroup group = new UserGroup();

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("mongo_pu");
        EntityManager em = emf.createEntityManager();

        Query delete = em.createNamedQuery("delete from User");
        delete.executeUpdate();
        Query delete1 = em.createNamedQuery("delete from UserGroup");
        delete1.executeUpdate();
        Query delete2=em.createNamedQuery("delete from PermissionSubject");
        delete2.executeUpdate();

        group.getUsers().add(me);
        em.persist(group);
        em.flush();

        String idGroup=group.getId();

        em.clear();   //Clear cache before finding record

        String findById = "Select g from UserGroup g where u.id=:id";
        Query query = em.createQuery(findById);
        query.setParameter("id", idGroup);
        UserGroup get= (UserGroup) query.getSingleResult();
        User meGet=get.getUsers().get(0);
        System.out.println("EZ az ember aki bennvan: "+ meGet);

        em.close();
        emf.close();
        System.in.read();
    }
}
