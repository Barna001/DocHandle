import Pojo.Document;
import Pojo.User;
import Pojo.UserGroup;
import Pojo.UserRoleEnum;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Barna on 2016.04.30..
 */
public class UserTest {

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("mongo_pu");
    EntityManager em = emf.createEntityManager();

    @Before
    public void deleteAll() {
        em.createQuery("delete from User").executeUpdate();
        em.createQuery("delete from UserGroup").executeUpdate();
        em.createQuery("delete from Document ").executeUpdate();
    }

    @Test
    public void testCreation() {
        User user = new User("Barna", UserRoleEnum.SUPER_ADMIN);
        user.setGroups(new ArrayList<UserGroup>() {{
            add(new UserGroup("New Group"));
        }});
        Assert.assertEquals("Barna", user.getName());
        Assert.assertEquals(UserRoleEnum.SUPER_ADMIN, user.getRole());
        Assert.assertEquals("New Group", user.getGroups().get(0).getName());
    }

    @Test
    public void testPersist() {
        User user = new User("Barna", UserRoleEnum.SUPER_ADMIN);
        em.persist(user);
        em.flush();
        User dbUser = em.createQuery("select u from User u", User.class).getSingleResult();
        Assert.assertEquals("Barna", dbUser.getName());
        Assert.assertEquals(UserRoleEnum.SUPER_ADMIN.toString(), dbUser.getRole().toString());
    }

    @Test
    public void testPersistCascadeGroup() {
        User user = new User("Barna", UserRoleEnum.SUPER_ADMIN);
        List<UserGroup> userGroupList = new ArrayList<>();
        userGroupList.add(new UserGroup("New Group Cascade"));
        user.setGroups(userGroupList);
        em.persist(user);
        em.flush();

        User dbUser = em.createQuery("select u from User u", User.class).getSingleResult();
        Assert.assertEquals(1, dbUser.getGroups().size());
        Assert.assertEquals("New Group Cascade", dbUser.getGroups().get(0).getName());
    }

    @Test
    public void testPersistCascadeGroupBidirectionally() {
        User user = new User("Barna", UserRoleEnum.SUPER_ADMIN);
        List<UserGroup> userGroupList = new ArrayList<>();
        userGroupList.add(new UserGroup("New Group Cascade"));
        user.setGroups(userGroupList);
        em.persist(user);
        em.flush();
        User dbUser = em.createQuery("select u from User u", User.class).getSingleResult();
        Assert.assertEquals(1, dbUser.getGroups().size());
        Assert.assertEquals("Barna", dbUser.getGroups().get(0).getUsers().get(0).getName());
    }

    @Test
    public void testOwnDocuments() {
        User user = new User("Barna", UserRoleEnum.ADMIN);
        Document document = new Document("OwnedByBarna", "simple doc", user);
        user.getOwnDocuments().add(document);
        em.persist(user);
        em.flush();
        List<Document> dbDocument = em.createQuery("select u from User u", User.class).getSingleResult().getOwnDocuments();
        Assert.assertEquals(1, dbDocument.size());
        Assert.assertEquals("Barna", dbDocument.get(0).getOwner().getName());
    }

    @Test(expected = NoResultException.class)
    public void deleteUser() {
        User user = new User("Barna", UserRoleEnum.SUPER_ADMIN);
        em.persist(user);
        em.flush();
        em.remove(user);
        em.flush();
        em.createQuery("select u from User u", User.class).getSingleResult();
    }

    @Test
    public void deleteUserButGroupAndDocumentExists() {
        User user = new User("Barna", UserRoleEnum.SUPER_ADMIN);
        List<UserGroup> userGroupList = new ArrayList<UserGroup>();
        userGroupList.add(new UserGroup("deleteUserButGroupAndDocumentExists"));
        user.setGroups(userGroupList);
        user.getOwnDocuments().add(new Document("deleteUserButGroupAndDocumentExists", "simple doc", user));
        em.persist(user);
        em.flush();
        em.remove(user);
        em.flush();
        UserGroup dbGroup = em.createQuery("select ug from UserGroup ug", UserGroup.class).getSingleResult();
        Document dbOwned = em.createQuery("select d from Document d", Document.class).getSingleResult();
        Assert.assertEquals("deleteUserButGroupAndDocumentExists", dbGroup.getName());
        Assert.assertEquals("deleteUserButGroupAndDocumentExists", dbOwned.getName());
    }
}
