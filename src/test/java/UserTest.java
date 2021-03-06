import application.Util;
import org.junit.*;
import pojo.Document;
import pojo.User;
import pojo.UserGroup;
import pojo.UserRoleEnum;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Barna on 2016.04.30..
 */
public class UserTest {

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
        em.createQuery("delete from Document ").executeUpdate();
        em.createQuery("delete from User").executeUpdate();
        em.createQuery("delete from UserGroup").executeUpdate();
        transaction.commit();
        Util.begin(transaction);
    }

    @Test
    public void testCreation() {
        User user = new User("Barna", UserRoleEnum.SUPER_ADMIN);
        user.setGroups(new ArrayList<UserGroup>() {{
            add(new UserGroup("New Group"));
            add(new UserGroup("G2"));
        }});
        Assert.assertEquals("Barna", user.getName());
        Assert.assertEquals(UserRoleEnum.SUPER_ADMIN, user.getRole());
        Assert.assertEquals("New Group", user.getGroups().get(0).getName());
        Assert.assertEquals("New Group,G2", user.getGroupNames());
    }

    @Test
    public void testPersist() {
        User user = new User("Barna", UserRoleEnum.SUPER_ADMIN);
        em.persist(user);
        transaction.commit();
        User dbUser = em.createQuery("select u from User u", User.class).getSingleResult();
        Assert.assertEquals("Barna", dbUser.getName());
        Assert.assertEquals(UserRoleEnum.SUPER_ADMIN.toString(), dbUser.getRole().toString());
    }

    @Test
    public void testPersistCascadeGroup() {
        User user = new User("Barna", UserRoleEnum.SUPER_ADMIN);
        UserGroup userGroup = new UserGroup("New Group Cascade");
        em.persist(userGroup);
        List<UserGroup> groups = new ArrayList<>();
        groups.add(userGroup);
        user.setGroups(groups);
        em.merge(user);
        transaction.commit();
        User dbUser = em.createQuery("select u from User u", User.class).getSingleResult();
        Assert.assertEquals(1, dbUser.getGroups().size());
        Assert.assertEquals("New Group Cascade", dbUser.getGroups().get(0).getName());
        Assert.assertEquals("New Group Cascade", dbUser.getGroupNames());
    }

    @Test
    public void testPersistCascadeGroupBidirectionally() {
        User user = new User("Barna", UserRoleEnum.SUPER_ADMIN);
        UserGroup userGroup = new UserGroup("New Group Cascade");
        em.persist(user);
        em.persist(userGroup);
        List<UserGroup> groups = new ArrayList<>();
        groups.add(userGroup);
        user.setGroups(groups);
        List<User> users = new ArrayList<>();
        users.add(user);
        userGroup.setUsers(users);
        em.merge(user);
        transaction.commit();
        Util.begin(transaction);
        User dbUser = em.createQuery("select u from User u", User.class).getSingleResult();
        UserGroup dbGroup = em.createQuery("select g from UserGroup g", UserGroup.class).getSingleResult();
        Assert.assertEquals(1, dbUser.getGroups().size());
        Assert.assertEquals(1, dbGroup.getUsers().size());
        Assert.assertEquals("Barna", dbUser.getGroups().get(0).getUsers().get(0).getName());
        Assert.assertEquals("New Group Cascade", dbGroup.getUsers().get(0).getGroups().get(0).getName());
    }

    @Test
    public void testOwnDocuments() {
        User user = new User("Barna", UserRoleEnum.ADMIN);
        Document document = new Document("OwnedByBarna", "simple doc", user);
        user.getOwnDocuments().add(document);
        em.merge(user);
        transaction.commit();
        List<Document> dbDocuments = em.createQuery("select u from User u", User.class).getSingleResult().getOwnDocuments();
        Assert.assertEquals(1, dbDocuments.size());
        Assert.assertEquals("Barna", dbDocuments.get(0).getOwner().getName());
    }

    @Test(expected = NoResultException.class)
    public void deleteUser() {
        User user = new User("Barna", UserRoleEnum.SUPER_ADMIN);
        em.persist(user);

        em.remove(user);

        em.createQuery("select u from User u", User.class).getSingleResult();
    }

    @Test
    public void deleteUserButGroupAndDocumentExists() {
        User user = new User("Barna", UserRoleEnum.SUPER_ADMIN);
        List<UserGroup> userGroupList = new ArrayList<UserGroup>();
        userGroupList.add(new UserGroup("deleteUserButGroupAndDocumentExists"));
        user.setGroups(userGroupList);
        user.getOwnDocuments().add(new Document("deleteUserButGroupAndDocumentExists", "simple doc", user));
        em.merge(user);
        transaction.commit();
        Util.begin(transaction);
        em.remove(user);
        transaction.commit();
        UserGroup dbGroup = em.createQuery("select ug from UserGroup ug", UserGroup.class).getSingleResult();
        Document dbOwned = em.createQuery("select d from Document d", Document.class).getSingleResult();
        Assert.assertEquals("deleteUserButGroupAndDocumentExists", dbGroup.getName());
        Assert.assertEquals("deleteUserButGroupAndDocumentExists", dbOwned.getName());
    }

    @After
    public void tearDown() throws Exception {
        em.close();
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
        emf.close();
    }
}
