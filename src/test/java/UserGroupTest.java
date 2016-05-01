import Pojo.User;
import Pojo.UserGroup;
import Pojo.UserRoleEnum;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Barna on 2016.04.30..
 */
public class UserGroupTest {

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("mongo_pu");
    EntityManager em = emf.createEntityManager();

    @Before
    public void deleteAll() {
        em.createQuery("delete from User").executeUpdate();
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
        em.flush();
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
        em.flush();
        UserGroup dbUserGroup = em.createQuery("select g from UserGroup g", UserGroup.class).getSingleResult();
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
        em.flush();
        UserGroup dbUserGroup = em.createQuery("select g from UserGroup g", UserGroup.class).getSingleResult();
        Assert.assertEquals("groupPersist", dbUserGroup.getUsers().get(0).getGroups().get(0).getName());
    }

}
