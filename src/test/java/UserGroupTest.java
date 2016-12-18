import application.Util;
import org.junit.*;
import pojo.User;
import pojo.UserGroup;
import pojo.UserRoleEnum;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.ArrayList;

/**
 * Created by Barna on 2016.04.30..
 */
public class UserGroupTest {

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
        em.createQuery("delete from User").executeUpdate();
        em.createQuery("delete from UserGroup").executeUpdate();
        transaction.commit();
        Util.begin(transaction);
    }

    @Test
    public void testCreation() {
        UserGroup userGroup = new UserGroup("group");
        userGroup.setUsers(new ArrayList<User>() {{
            add(new User("Barna", UserRoleEnum.SUPER_ADMIN));
        }});
        Assert.assertEquals("group", userGroup.getName());
        Assert.assertEquals(UserRoleEnum.SUPER_ADMIN, userGroup.getUsers().get(0).getRole());
        Assert.assertEquals("Barna", userGroup.getUsers().get(0).getName());
    }

    @Test
    public void testPersist() {
        UserGroup userGroup = new UserGroup("group");
        em.persist(userGroup);
        transaction.commit();
        UserGroup dbUserGroup = em.createQuery("select g from UserGroup g", UserGroup.class).getSingleResult();
        Assert.assertEquals("group", dbUserGroup.getName());
    }

    @Test
    public void testUserAlreadyPersistedAndItIsPutInNewGroup() {
        User user = new User("userName", UserRoleEnum.GUEST);
        em.persist(user);
        transaction.commit();
        Util.begin(transaction);
        UserGroup group = new UserGroup("groupName");
        group.getUsers().add(user);
        em.merge(group);
        transaction.commit();
        UserGroup dbUserGroup = em.createQuery("select g from UserGroup g", UserGroup.class).getSingleResult();
        User dbUser = em.createQuery("select u from User u", User.class).getSingleResult();
        Assert.assertEquals("userName", dbUserGroup.getUsers().get(0).getName());
        Assert.assertEquals("groupName", dbUser.getGroups().get(0).getName());
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
