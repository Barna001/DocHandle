import application.Util;
import org.junit.*;
import pojo.*;

import javax.management.InvalidAttributeValueException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

/**
 * Created by Barna on 2016.05.04..
 */
public class AccessTest {

    private static EntityManagerFactory emf;
    private EntityManager em;
    EntityTransaction transaction;

    @BeforeClass
    public static void SetUpBeforeClass() throws Exception {
        emf = Persistence.createEntityManagerFactory("test_pu");
    }

    @Before
    public void deleteAll() {
        em = emf.createEntityManager();
        transaction = em.getTransaction();
        Util.begin(transaction);
        em.createQuery("delete from Access ").executeUpdate();
        em.createQuery("delete from Document ").executeUpdate();
        em.createQuery("delete from User").executeUpdate();
        em.createQuery("delete from UserGroup").executeUpdate();
        transaction.commit();
        Util.begin(transaction);
    }

    @Test
    public void testCreation() throws InvalidAttributeValueException {
        User user = new User("Barna", UserRoleEnum.ADMIN);
        Document doc = new Document("Doc", "content", user);
        Access access = new Access(user, doc, AccessTypeEnum.DELETE, 1000);
        Assert.assertEquals("Barna", access.getWho().getName());
        Assert.assertEquals("content", access.getWhat().getContent());
        Assert.assertEquals(1000, access.getPriority());
    }

    @Test
    public void testPersistWithUser() throws InvalidAttributeValueException {
        User user = new User("Barna", UserRoleEnum.ADMIN);
        Document doc = new Document("Doc", "content", user);
        em.persist(user);
        em.persist(doc);
        transaction.commit();
        Util.begin(transaction);
        Access access = new Access(user, doc, AccessTypeEnum.DELETE, 1000);
        em.persist(access);
        transaction.commit();
        Util.begin(transaction);
        Access dbAccess = em.createQuery("select a from Access a", Access.class).getSingleResult();
        Assert.assertEquals("Barna", dbAccess.getWho().getName());
        Assert.assertEquals("Doc", dbAccess.getWhat().getName());
        Assert.assertEquals(1000, dbAccess.getPriority());
        Assert.assertEquals(AccessTypeEnum.DELETE, access.getType());
    }

    @Test
    public void testPersistWithUserGroup() throws InvalidAttributeValueException {
        UserGroup group = new UserGroup("group");
        User user = new User("username", UserRoleEnum.ADMIN);
        Document doc = new Document("Doc", "content", user);
        em.persist(group);
        em.persist(user);
        em.persist(doc);
        transaction.commit();
        Util.begin(transaction);
        Access access = new Access(group, doc, AccessTypeEnum.DENY, 1);
        em.persist(access);
        transaction.commit();
        Util.begin(transaction);
        Access dbAccess = em.createQuery("select a from Access a", Access.class).getSingleResult();
        Assert.assertEquals("group", dbAccess.getWho().getName());
        Assert.assertEquals("Doc", dbAccess.getWhat().getName());
        Assert.assertEquals(1, dbAccess.getPriority());
        Assert.assertEquals(AccessTypeEnum.DENY, access.getType());
    }

    @Test(expected = InvalidAttributeValueException.class)
    public void testLessThanMinimumPrio() throws InvalidAttributeValueException {
        User user = new User("Barna", UserRoleEnum.ADMIN);
        Document doc = new Document("Doc", "content", user);
        Access access = new Access(user, doc, AccessTypeEnum.DELETE, 0);
    }

    @Test(expected = InvalidAttributeValueException.class)
    public void testMoreThanMaximumPrio() throws InvalidAttributeValueException {
        User user = new User("Barna", UserRoleEnum.ADMIN);
        Document doc = new Document("Doc", "content", user);
        Access access = new Access(user, doc, AccessTypeEnum.DELETE, 10001);
    }

    @After
    public void tearDown() throws Exception {
        transaction.commit();
        em.close();
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
        emf.close();
    }
}
