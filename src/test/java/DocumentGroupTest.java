import Pojo.Document;
import Pojo.DocumentGroup;
import org.junit.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.print.Doc;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Barna on 2016.05.03..
 */
public class DocumentGroupTest {

    private static EntityManagerFactory emf;
    private EntityManager em;

    @BeforeClass
    public static void SetUpBeforeClass() throws Exception {
        emf = Persistence.createEntityManagerFactory("mongo_pu");
    }

    @Before
    public void deleteAll() {
        em = emf.createEntityManager();
        em.createQuery("delete from Document ").executeUpdate();
        em.createQuery("delete from PermissionSubject").executeUpdate();
        em.createQuery("delete from DocumentGroup ").executeUpdate();
    }

    @Test
    public void testCreation() {
        DocumentGroup documentGroup = new DocumentGroup("group", "description");
        documentGroup.setDocuments(new ArrayList<Document>() {{
            add(new Document("doc", "cont", null));
        }});
        Assert.assertEquals("group", documentGroup.getName());
        Assert.assertEquals("description", documentGroup.getDescription());
        Assert.assertEquals("doc", documentGroup.getDocuments().get(0).getName());
    }

    @Test
    public void TestPersist() {
        DocumentGroup docGroup = new DocumentGroup("groupPersist", "descPersist");
        em.persist(docGroup);

        DocumentGroup dbDocGroup = em.createQuery("select g from DocumentGroup g", DocumentGroup.class).getSingleResult();
        Assert.assertEquals("groupPersist", dbDocGroup.getName());
        Assert.assertEquals("descPersist", dbDocGroup.getDescription());
    }

    @Test
    public void testPersistCascadeDocument() {
        DocumentGroup docGroup = new DocumentGroup("groupPersist", "descPersist");
        List<Document> list = new ArrayList<>();
        list.add(new Document("doc", "cont", null));
        docGroup.setDocuments(list);
        em.persist(docGroup);

        DocumentGroup dbDocGroup = em.createQuery("select g from DocumentGroup g", DocumentGroup.class).getSingleResult();
        Assert.assertEquals(1, dbDocGroup.getDocuments().size());
        Document dbDocument = dbDocGroup.getDocuments().get(0);
        Assert.assertEquals("doc", dbDocument.getName());
        Assert.assertEquals("cont", dbDocument.getContent());
        Assert.assertNull(dbDocument.getOwner());
    }


    @Test
    public void testPersistCascadeDocumentBidirectionally() {
        DocumentGroup docGroup = new DocumentGroup("groupPersist", "descPersist");
        List<Document> list = new ArrayList<>();
        list.add(new Document("doc", "cont", null));
        docGroup.setDocuments(list);
        em.persist(docGroup);

        DocumentGroup dbDocGroup = em.createQuery("select g from DocumentGroup g", DocumentGroup.class).getSingleResult();
        DocumentGroup documentContainingGroup = dbDocGroup.getDocuments().get(0).getContainingGroups().get(0);
        Assert.assertEquals(1, documentContainingGroup.getDocuments().size());
        Assert.assertEquals("groupPersist", documentContainingGroup.getName());
    }

    @Test
    public void testDocumentAlreadyPersistedAndItIsPutInNewGroup() {
        Document doc = new Document("docName", "docCont", null);
        em.persist(doc);


        DocumentGroup group = new DocumentGroup("docGroup", "docDescr");
        group.getDocuments().add(doc);
        em.persist(group);


        DocumentGroup dbDocGroup = em.createQuery("select g from DocumentGroup g", DocumentGroup.class).getSingleResult();
        Document dbDoc = em.createQuery("select d from Document d", Document.class).getSingleResult();
        Assert.assertEquals("docName", dbDocGroup.getDocuments().get(0).getName());
        Assert.assertEquals("docGroup", dbDoc.getContainingGroups().get(0).getName());
    }

    @After
    public void tearDown() throws Exception {
        em.close();
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
        //MongoUtils.dropDatabase(emf, "mongo_pu");
        emf.close();
    }

}
