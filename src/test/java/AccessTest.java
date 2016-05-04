import Pojo.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.management.InvalidAttributeValueException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Created by Barna on 2016.05.04..
 */
public class AccessTest {
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("mongo_pu");
    EntityManager em = emf.createEntityManager();

    @Before
    public void deleteAll() {
        em.createQuery("delete from Document ").executeUpdate();
        em.createQuery("delete from User").executeUpdate();
        em.createQuery("delete from UserGroup").executeUpdate();
        em.createQuery("delete from PermissionSubject").executeUpdate();
        em.createQuery("delete from Access ").executeUpdate();
    }

    @Test
    public void testCreation() throws InvalidAttributeValueException {
        User user = new User("Barna", UserRoleEnum.ADMIN);
        Document doc = new Document("Doc", "content", null);
        Access access = new Access(user, doc, AccessTypeEnum.DELETE, 1000);
        Assert.assertEquals("Barna", access.getWho().getName());
        Assert.assertEquals("content", access.getWhat().getContent());
        Assert.assertEquals(1000, access.getPriority());
    }

    @Test
    public void testPersistWithUser() throws InvalidAttributeValueException {
        User user = new User("Barna", UserRoleEnum.ADMIN);
        Document doc = new Document("Doc", "content", null);
        em.persist(user);
        em.persist(doc);
        em.flush();
        Access access = new Access(user, doc, AccessTypeEnum.DELETE, 1000);
        em.persist(access);

        Access dbAccess = em.createQuery("select a from Access a", Access.class).getSingleResult();
        Assert.assertEquals("Barna", dbAccess.getWho().getName());
        Assert.assertEquals("Doc", dbAccess.getWhat().getName());
        Assert.assertEquals(1000, dbAccess.getPriority());
        Assert.assertEquals(AccessTypeEnum.DELETE, access.getType());
    }

    @Test
    public void testPersistWithUserGroup() throws InvalidAttributeValueException {
        UserGroup group = new UserGroup("group");
        Document doc = new Document("Doc", "content", null);
        em.persist(group);
        em.persist(doc);
        em.flush();
        Access access = new Access(group, doc, AccessTypeEnum.DENY, 1);
        em.persist(access);

        Access dbAccess = em.createQuery("select a from Access a", Access.class).getSingleResult();
        Assert.assertEquals("group", dbAccess.getWho().getName());
        Assert.assertEquals("Doc", dbAccess.getWhat().getName());
        Assert.assertEquals(1, dbAccess.getPriority());
        Assert.assertEquals(AccessTypeEnum.DENY, access.getType());
    }

    @Test(expected = InvalidAttributeValueException.class)
    public void testLessThanMinimumPrio() throws InvalidAttributeValueException {
        User user = new User("Barna", UserRoleEnum.ADMIN);
        Document doc = new Document("Doc", "content", null);
        Access access = new Access(user, doc, AccessTypeEnum.DELETE, 0);
    }

    @Test(expected = InvalidAttributeValueException.class)
    public void testMoreThanMaximumPrio() throws InvalidAttributeValueException {
        User user = new User("Barna", UserRoleEnum.ADMIN);
        Document doc = new Document("Doc", "content", null);
        Access access = new Access(user, doc, AccessTypeEnum.DELETE, 10001);
    }
}
