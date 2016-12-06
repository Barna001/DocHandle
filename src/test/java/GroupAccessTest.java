import application.Util;
import org.junit.*;
import pojo.*;

import javax.management.InvalidAttributeValueException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

/**
 * Created by Barna on 2016.05.05..
 */
public class GroupAccessTest {

    private static EntityManagerFactory emf;
    private EntityManager em;
    EntityTransaction transaction;


    @BeforeClass
    public static void SetUpBeforeClass() throws Exception {
        emf = Util.getTestFactory();
    }

    @Before
    public void deleteAll() {
        em = emf.createEntityManager();
        transaction = em.getTransaction();
        em.createQuery("delete from User").executeUpdate();
        em.createQuery("delete from UserGroup").executeUpdate();
        em.createQuery("delete from PermissionSubject").executeUpdate();
        em.createQuery("delete from DocumentGroup").executeUpdate();
        em.createQuery("delete from GroupAccess ").executeUpdate();
        transaction.commit();
        Util.begin(transaction);
    }

    @Test
    public void testCreation() throws InvalidAttributeValueException {
        User user = new User("Barna", UserRoleEnum.ADMIN);
        DocumentGroup docGroup = new DocumentGroup("Doc", "desc");
        GroupAccess gAccess = new GroupAccess(user, docGroup, GroupAccessTypeEnum.RENAME, 1000);
        Assert.assertEquals("Barna", gAccess.getWho().getName());
        Assert.assertEquals("desc", gAccess.getWhat().getDescription());
        Assert.assertEquals(1000, gAccess.getPriority());
        Assert.assertEquals(GroupAccessTypeEnum.RENAME, gAccess.getType());
    }

    @Test
    public void testPersistWithUser() throws InvalidAttributeValueException {
        User user = new User("Barna", UserRoleEnum.ADMIN);
        DocumentGroup group = new DocumentGroup("DocGroup", "content");
        em.persist(user);
        em.persist(group);

        GroupAccess access = new GroupAccess(user, group, GroupAccessTypeEnum.ADD_DOCUMENT, 1000);
        em.persist(access);

        GroupAccess dbAccess = em.createQuery("select a from GroupAccess a", GroupAccess.class).getSingleResult();
        Assert.assertEquals("Barna", dbAccess.getWho().getName());
        Assert.assertEquals("DocGroup", dbAccess.getWhat().getName());
        Assert.assertEquals(1000, dbAccess.getPriority());
        Assert.assertEquals(GroupAccessTypeEnum.ADD_DOCUMENT, access.getType());
    }

    @Test
    public void testPersistWithUserGroup() throws InvalidAttributeValueException {
        UserGroup group = new UserGroup("group");
        DocumentGroup docGroup = new DocumentGroup("DocG", "cont");
        em.persist(group);
        em.persist(docGroup);

        GroupAccess access = new GroupAccess(group, docGroup, GroupAccessTypeEnum.DENY, 1);
        em.persist(access);

        GroupAccess dbAccess = em.createQuery("select a from GroupAccess a", GroupAccess.class).getSingleResult();
        Assert.assertEquals("group", dbAccess.getWho().getName());
        Assert.assertEquals("DocG", dbAccess.getWhat().getName());
        Assert.assertEquals(1, dbAccess.getPriority());
        Assert.assertEquals(GroupAccessTypeEnum.DENY, access.getType());
    }

    @Test(expected = InvalidAttributeValueException.class)
    public void testLessThanMinimumPrio() throws InvalidAttributeValueException {
        User user = new User("Barna", UserRoleEnum.ADMIN);
        DocumentGroup doc = new DocumentGroup("DocGroup", "content");
        GroupAccess access = new GroupAccess(user, doc, GroupAccessTypeEnum.DESCRIPTION, 0);
    }

    @Test(expected = InvalidAttributeValueException.class)
    public void testMoreThanMaximumPrio() throws InvalidAttributeValueException {
        User user = new User("Barna", UserRoleEnum.ADMIN);
        DocumentGroup doc = new DocumentGroup("Doc", "content");
        GroupAccess access = new GroupAccess(user, doc, GroupAccessTypeEnum.DENY, 10001);
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
