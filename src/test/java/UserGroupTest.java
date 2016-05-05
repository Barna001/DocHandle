import Pojo.User;
import Pojo.UserGroup;
import Pojo.UserRoleEnum;
import org.junit.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Barna on 2016.04.30..
 */
public class UserGroupTest {

    private static EntityManagerFactory emf;
    private EntityManager em;

    @BeforeClass
    public static void SetUpBeforeClass() throws Exception {
        emf = Persistence.createEntityManagerFactory("test_pu");
    }

    @Before
    public void deleteAll() {
        em = emf.createEntityManager();
        em.createQuery("delete from User").executeUpdate();
        em.createQuery("delete from PermissionSubject").executeUpdate();
        em.createQuery("delete from UserGroup").executeUpdate();
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

        UserGroup dbUserGroup = em.createQuery("select g from UserGroup g", UserGroup.class).getSingleResult();
        Assert.assertEquals("group", dbUserGroup.getName());
    }

    @Test
    public void testPersistCascadeUser() {
        UserGroup userGroup = new UserGroup("groupPersist");
        List<User> list = new ArrayList<>();
        list.add(new User("Barna", UserRoleEnum.SUPER_ADMIN));
        userGroup.setUsers(list);
        em.persist(userGroup);

        UserGroup dbUserGroup = em.createQuery("select g from UserGroup g", UserGroup.class).getSingleResult();
        Assert.assertEquals(1, dbUserGroup.getUsers().size());
        Assert.assertEquals("Barna", dbUserGroup.getUsers().get(0).getName());
        Assert.assertEquals(UserRoleEnum.SUPER_ADMIN, dbUserGroup.getUsers().get(0).getRole());
    }

    @Test
    public void testPersistCascadeUserBidirectionally() {
        UserGroup userGroup = new UserGroup("groupPersist");
        List<User> userList = new ArrayList<>();
        userList.add(new User("Barna", UserRoleEnum.SUPER_ADMIN));
        userGroup.setUsers(userList);
        em.persist(userGroup);

        UserGroup dbUserGroup = em.createQuery("select g from UserGroup g", UserGroup.class).getSingleResult();
        List<UserGroup> usersGroups = dbUserGroup.getUsers().get(0).getGroups();
        Assert.assertEquals(1, usersGroups.size());
        Assert.assertEquals("groupPersist", usersGroups.get(0).getName());
    }

    @Test
    public void testUserAlreadyPersistedAndItIsPutInNewGroup() {
        User user = new User("userName", UserRoleEnum.GUEST);
        em.persist(user);


        UserGroup group = new UserGroup("groupName");
        group.getUsers().add(user);
        em.persist(group);


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
