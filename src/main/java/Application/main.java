package Application;

import Pojo.*;
import Pojo.Access;

import javax.management.InvalidAttributeValueException;
import javax.persistence.*;
import java.io.IOException;
import java.util.List;

/**
 * Created by Barna on 2016.04.28..
 */
public class main {
    public static void main(String[] args) throws IOException, InvalidAttributeValueException {
        User me = new User("Barna", UserRoleEnum.SUPER_ADMIN);
        UserGroup group = new UserGroup();

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("mongo_pu");
        EntityManager em = emf.createEntityManager();

        deleteAll(em);

        group.getUsers().add(me);
        em.persist(group);

        Document document=new Document("doksi","tartalom",me);
        em.persist(document);

        Document document1 = new Document("doksikakakkak","eze",me);
        em.persist(document1);

        Access access=new Access(me,document,AccessTypeEnum.DELETE,200);
        em.persist(access);
        Access access1=new Access(me,document1,AccessTypeEnum.DENY,1000);
        em.persist(access1);


        String idGroup=group.getId();

        em.clear();   //Clear cache before finding record

        String findById = "Select g from UserGroup g where u.id=:id";
        Query query = em.createQuery(findById);
        query.setParameter("id", idGroup);
        UserGroup get= (UserGroup) query.getSingleResult();
        User meGet=get.getUsers().get(0);
        System.out.println("EZ az ember aki bennvan: "+ meGet);

        TypedQuery<Access> query1 = em.createQuery("select a from Access a where a.who=:userId",Access.class).setParameter("userId",meGet.getId());
        List<Access> getDocumentsForUser= query1.getResultList();
        StringBuilder sb = new StringBuilder();
        for (Access a : getDocumentsForUser) {
            sb.append(a.getWhat()).append("\n");
        }
        System.out.println("Ezekhez a doksikhoz fér hozzá:\n"+sb.toString());

        em.close();
        emf.close();
        System.in.read();
    }

    private static void deleteAll(EntityManager em) {
        Query delete = em.createNamedQuery("delete from User");
        delete.executeUpdate();
        Query delete1 = em.createNamedQuery("delete from UserGroup");
        delete1.executeUpdate();
        Query delete2=em.createNamedQuery("delete from PermissionSubject");
        delete2.executeUpdate();
        Query delete3=em.createNamedQuery("delete from Document");
        delete3.executeUpdate();
        Query delete4=em.createNamedQuery("delete from DocumentGroup");
        delete4.executeUpdate();
        Query delete5=em.createNamedQuery("delete from Access");
        delete5.executeUpdate();


    }
}
