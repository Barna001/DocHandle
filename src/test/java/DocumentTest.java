import Pojo.Document;
import Pojo.DocumentGroup;
import Pojo.User;
import Pojo.UserRoleEnum;
import org.junit.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Barna on 2016.05.01..
 */
public class DocumentTest {

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
        em.createQuery("delete from Document ").executeUpdate();
        em.createQuery("delete from PermissionSubject").executeUpdate();
        em.createQuery("delete from DocumentGroup Group").executeUpdate();
    }

    @Test
    public void testCreation() {
        Document document = new Document("document", "content", null);
        document.setContainingGroups(new ArrayList<DocumentGroup>() {{
            add(new DocumentGroup("newGroup", "groupDescription"));
        }});
        document.setOwner(new User("userName", UserRoleEnum.GUEST));
        Assert.assertEquals("document", document.getName());
        Assert.assertEquals("content", document.getContent());
        Assert.assertEquals(1, document.getContainingGroups().size());
        Assert.assertEquals("newGroup", document.getContainingGroups().get(0).getName());
        Assert.assertEquals("userName", document.getOwner().getName());
    }

    @Test
    public void testPersist() {
        Document document = new Document("document", "content", null);
        ArrayList<DocumentGroup> groups = new ArrayList<>();
        groups.add(new DocumentGroup("newGroup", "desc"));
        document.setContainingGroups(groups);
        document.setOwner(new User("userName", UserRoleEnum.GUEST));
        em.persist(document);

        Document dbDocument = em.createQuery("select d from Document d", Document.class).getSingleResult();
        Assert.assertEquals("document", dbDocument.getName());
        Assert.assertEquals("content", dbDocument.getContent());

    }

    @Test
    public void testPersistCascade() {
        Document document = new Document("document", "content", null);
        ArrayList<DocumentGroup> groups = new ArrayList<>();
        groups.add(new DocumentGroup("newGroup", "desc"));
        document.setContainingGroups(groups);
        document.setOwner(new User("userName", UserRoleEnum.GUEST));
        em.persist(document);

        Document dbDocument = em.createQuery("select d from Document d", Document.class).getSingleResult();
        Assert.assertEquals(1, dbDocument.getContainingGroups().size());
        Assert.assertEquals("newGroup", dbDocument.getContainingGroups().get(0).getName());
        Assert.assertEquals(1, dbDocument.getOwner().getOwnDocuments().size());
        Assert.assertEquals("userName", dbDocument.getOwner().getName());
    }

    @Test
    public void testPersistCascadeBidirectional() {
        Document document = new Document("document", "content", null);
        ArrayList<DocumentGroup> groups = new ArrayList<>();
        groups.add(new DocumentGroup("newGroup", "desc"));
        document.setContainingGroups(groups);
        document.setOwner(new User("userName", UserRoleEnum.GUEST));
        em.persist(document);

        Document dbDocument = em.createQuery("select d from Document d", Document.class).getSingleResult();
        Assert.assertEquals("content", dbDocument.getContainingGroups().get(0).getDocuments().get(0).getContent());
        Assert.assertEquals("content", dbDocument.getOwner().getOwnDocuments().get(0).getContent());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testImmutableList() {
        Document document = new Document("document", "content", new User());
        document.setContainingGroups(new ArrayList<DocumentGroup>() {{
            add(new DocumentGroup("groupName", "group desc"));
        }});
        document.getContainingGroups().add(new DocumentGroup());
    }

    @Test
    public void testImmutableListModifyElement() {
        Document document = new Document("document", "content", new User());
        document.setContainingGroups(new ArrayList<DocumentGroup>() {{
            add(new DocumentGroup("groupName", "group desc"));
        }});
        document.getContainingGroups().get(0).setName("newName");
        Assert.assertEquals("newName", document.getContainingGroups().get(0).getName());
    }

    @Test
    public void testAddDocumentGroup() {
        Document document = new Document("document", "content", new User());
        Assert.assertEquals(0, document.getContainingGroups().size());
        document.addDocumentGroup(new DocumentGroup("added", ""));
        Assert.assertEquals(1, document.getContainingGroups().size());
        Assert.assertEquals("added", document.getContainingGroups().get(0).getName());
    }

    @Test
    public void removeDocumentGroup() {
        Document document = new Document("document", "content", new User());
        document.setContainingGroups(new ArrayList<DocumentGroup>() {{
            add(new DocumentGroup("groupName", "group desc"));
        }});
        Assert.assertEquals(1, document.getContainingGroups().size());
        document.removeDocumentGroup(document.getContainingGroups().get(0));
        Assert.assertEquals(0, document.getContainingGroups().size());
    }

    @Test(expected = NoResultException.class)
    public void deleteDocument() {
        Document document = new Document("document", "content", new User());
        em.persist(document);

        em.remove(document);

        Document dbDocument = em.createQuery("select d from Document d", Document.class).getSingleResult();
    }

    @Test
    public void deleteDocumentButOwnerAndDocumentGroupExists() {
        Document document = new Document("document", "content", null);
        ArrayList<DocumentGroup> groups = new ArrayList<>();
        groups.add(new DocumentGroup("deleteDocumentButOwnerAndDocumentGroupExists", "desc"));
        document.setContainingGroups(groups);
        document.setOwner(new User("deleteDocumentButOwnerAndDocumentGroupExists", UserRoleEnum.GUEST));
        em.persist(document);

        em.remove(document);

        User dbOwner = em.createQuery("select u from User u", User.class).getSingleResult();
        DocumentGroup dbGroup = em.createQuery("select g from DocumentGroup g", DocumentGroup.class).getSingleResult();
        Assert.assertEquals("deleteDocumentButOwnerAndDocumentGroupExists", dbOwner.getName());
        Assert.assertEquals("deleteDocumentButOwnerAndDocumentGroupExists", dbGroup.getName());
    }

    @Test
    public void testModificationDateEqualsCreationDateAfterConstruct() {
        Document document = new Document("document", "content", null);
        Assert.assertEquals(document.getCreationDate().toString(), document.getModificationDate().toString());
    }

    @Test
    public void testModificationDateIsDifferentFromCreationDateAfterModif() {
        Document document = new Document("document", "content", null);
        try {
            Thread.sleep(2000);//this is important, because commands below run under 1 sec, and Date would be the same
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        document.setName("newName");
        Assert.assertNotEquals(document.getCreationDate().toString(), document.getModificationDate().toString());
        Date prevModifDate = document.getModificationDate();
        try {
            Thread.sleep(2000);//this is important, because commands below run under 1 sec, and Date would be the same
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        document.addDocumentGroup(new DocumentGroup());
        Assert.assertNotEquals(prevModifDate.toString(), document.getModificationDate().toString());
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
