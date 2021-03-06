import application.Util;
import org.junit.*;
import pojo.Document;
import pojo.DocumentGroup;
import pojo.User;
import pojo.UserRoleEnum;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Barna on 2016.05.01..
 */
public class DocumentTest {

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
        em.createQuery("delete from DocumentGroup").executeUpdate();
        transaction.commit();
        Util.begin(transaction);
    }

    @Test
    public void testCreation() {
        Document document = new Document("document", "content", new User("Barna", UserRoleEnum.ADMIN));
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
        Document document = new Document("document", "content", new User("Barna", UserRoleEnum.ADMIN));
        ArrayList<DocumentGroup> groups = new ArrayList<>();
        groups.add(new DocumentGroup("newGroup", "desc"));
        document.setContainingGroups(groups);
        document.setOwner(new User("userName", UserRoleEnum.GUEST));
        em.persist(document);
        transaction.commit();
        Document dbDocument = em.createQuery("select d from Document d", Document.class).getSingleResult();
        Assert.assertEquals("document", dbDocument.getName());
        Assert.assertEquals("content", dbDocument.getContent());

    }

    @Test
    public void testPersistCascade() {
        Document document = new Document("document", "content", new User("Barna", UserRoleEnum.ADMIN));
        DocumentGroup dg = new DocumentGroup("newGroup", "desc");
        em.persist(dg);
        ArrayList<DocumentGroup> groups = new ArrayList<>();
        groups.add(dg);
        document.setContainingGroups(groups);
        User user = new User("userName", UserRoleEnum.GUEST);
        em.persist(user);
        document.setOwner(user);
        em.merge(document);
        transaction.commit();
        Document dbDocument = em.createQuery("select d from Document d", Document.class).getSingleResult();
        Assert.assertEquals(1, dbDocument.getContainingGroups().size());
        Assert.assertEquals("newGroup", dbDocument.getContainingGroups().get(0).getName());
        Assert.assertEquals("newGroup", dbDocument.getGroupNames());
        Assert.assertEquals(1, dbDocument.getOwner().getOwnDocuments().size());
        Assert.assertEquals("userName", dbDocument.getOwner().getName());
    }

    //Dont need it anymore, because you have to create a Group or User first, just after you can create a document for them
//    @Test
//    public void testPersistCascadeBidirectional() {
//        Document document = new Document("document", "content", null);
//        ArrayList<DocumentGroup> groups = new ArrayList<>();
//        groups.add(new DocumentGroup("newGroup", "desc"));
//        document.setContainingGroups(groups);
//        document.setOwner(new User("userName", UserRoleEnum.GUEST));
//        em.persist(document);
//
//        Document dbDocument = em.createQuery("select d from Document d", Document.class).getSingleResult();
//        Assert.assertEquals("content", dbDocument.getContainingGroups().get(0).getDocuments().get(0).getContent());
//        Assert.assertEquals("content", dbDocument.getOwner().getOwnDocuments().get(0).getContent());
//    }

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
        Document document = new Document("document", "content", new User("Barna", UserRoleEnum.ADMIN));
        ArrayList<DocumentGroup> groups = new ArrayList<>();
        DocumentGroup dg = new DocumentGroup("deleteDocumentButOwnerAndDocumentGroupExists", "desc");
        em.persist(dg);
        transaction.commit();
        Util.begin(transaction);
        groups.add(dg);
        document.setContainingGroups(groups);
        em.persist(document);
        transaction.commit();
        Util.begin(transaction);
        User user = new User("deleteDocumentButOwnerAndDocumentGroupExists", UserRoleEnum.GUEST);
        em.persist(user);
        transaction.commit();
        Util.begin(transaction);
        document.setOwner(user);
        em.flush();
        em.remove(document);
        transaction.commit();
        User dbOwner = em.createQuery("select u from User u", User.class).getSingleResult();
        DocumentGroup dbGroup = em.createQuery("select g from DocumentGroup g", DocumentGroup.class).getSingleResult();
        Assert.assertEquals("deleteDocumentButOwnerAndDocumentGroupExists", dbOwner.getName());
        Assert.assertEquals("deleteDocumentButOwnerAndDocumentGroupExists", dbGroup.getName());
    }

    @Test
    public void testModificationDateEqualsCreationDateAfterConstruct() {
        Document document = new Document("document", "content", new User("Barna", UserRoleEnum.ADMIN));
        Assert.assertEquals(document.getCreationDate().toString(), document.getModificationDate().toString());
    }

    @Test
    public void testModificationDateIsDifferentFromCreationDateAfterModif() {
        Document document = new Document("document", "content", new User("Barna", UserRoleEnum.ADMIN));
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

    @Test
    //todo test to create files too
    public void testCascadeWithFiles() {

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
