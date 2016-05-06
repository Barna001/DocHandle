import Pojo.Document;
import Pojo.File;
import Pojo.FileVersion;
import org.junit.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Created by Barna on 2016.05.06..
 */
public class FileTest {
    private static EntityManagerFactory emf;
    private EntityManager em;

    @BeforeClass
    public static void SetUpBeforeClass() throws Exception {
        emf = Persistence.createEntityManagerFactory("test_pu");
    }

    @Before
    public void deleteAll() {
        em = emf.createEntityManager();
        em.createQuery("delete from File").executeUpdate();
        em.createQuery("delete from Document").executeUpdate();
        em.createQuery("delete from FileVersion").executeUpdate();
    }

    @Test
    public void testCreation() {
        Document doc = new Document("doc", "content", null);
        File file = new File("file", doc);
        FileVersion version = new FileVersion(new byte[]{2, 3, 4},file);
        file.getVersionsList().add(version);
        Assert.assertEquals("file", file.getName());
        Assert.assertEquals(0, file.getLatestNumber());
        Assert.assertEquals(2, file.getLatestVersion().getData()[0]);
        Assert.assertEquals("content", file.getRootDocument().getContent());
    }

    @Test
    public void testGetLatestVersion() {
        File file = new File("file", null);
        FileVersion version = new FileVersion(new byte[]{2, 3, 4},file);
        file.getVersionsList().add(version);
        FileVersion getVersion = file.getLatestVersion();
        byte[] b = new byte[]{4};
        Assert.assertEquals(3, file.getLatestVersion().getData().length);
        Assert.assertEquals(b[0], getVersion.getData()[2]);
    }

    @Test
    public void testGetVersionByNumber() {
        File file = new File("file", null);
        FileVersion version = new FileVersion(new byte[]{2, 3, 4},file);
        FileVersion version2 = new FileVersion(new byte[]{1, 2, 3, 4, 5, 6},file);
        file.getVersionsList().add(version);
        file.getVersionsList().add(version2);
        byte[] b = new byte[]{2, 1};
        Assert.assertEquals(6, file.getLatestVersion().getData().length);
        Assert.assertEquals(b[0], file.getFileVersionByNumber(0).getData()[0]);
        Assert.assertEquals(b[1], file.getLatestVersion().getData()[0]);
        Assert.assertEquals(1, file.getLatestNumber());
    }

    @Test
    public void testRootFileOfVersion() {
        File file = new File("file", null);
        FileVersion version = new FileVersion(new byte[]{2, 3, 4},file);
        file.getVersionsList().add(version);
        Assert.assertEquals("file", version.getRootFile().getName());
    }

    @Test
    public void testPersist(){
        File file = new File("file",null);
        em.persist(file);
        File dbFile=em.createQuery("select f from File f",File.class).getSingleResult();
        Assert.assertEquals("file",dbFile.getName());
    }

    @Test
    public void testPersistWithDocument(){
        Document doc=new Document("doc","cont",null);
        File file=new File("file",doc);
        em.persist(doc);
        em.persist(file);
        Document dbDoc=em.createQuery("select f from File f",File.class).getSingleResult().getRootDocument();
        Assert.assertEquals("doc",dbDoc.getName());
    }

    @Test
    public void testPersistFileVersion(){
        FileVersion version = new FileVersion(new byte[]{2, 3, 4},null);
        em.persist(version);
        FileVersion dbVersion= em.createQuery("select v from FileVersion v",FileVersion.class).getSingleResult();
        byte[] b = new byte[]{2};
        Assert.assertEquals(b[0],dbVersion.getData()[0]);
    }

    @Test
    public void testPersistWithVersion() {
        File file = new File("file", null);
        em.persist(file);

        FileVersion version = new FileVersion(new byte[]{2, 3, 4},null);
        em.persist(version);

        file.getVersionsList().add(version);
        em.merge(file);
        em.refresh(file);
        em.flush();

        FileVersion dbVersion=em.createQuery("select v from FileVersion v",FileVersion.class).getSingleResult();
        File dbFile = em.createQuery("select f from File f", File.class).getSingleResult();
        Assert.assertEquals("file", dbFile.getName());
        byte[] b = new byte[]{2};
        Assert.assertEquals(b[0],dbFile.getLatestVersion().getData()[0]);
//        FileVersion dbFileVersion=em.createQuery("select v from FileVersion v",FileVersion.class).getSingleResult();
//        Assert.assertEquals(b[0],dbFileVersion.getData()[0]);
//        Assert.assertEquals(3, dbFile.getLatestVersion().getData().length);
//        Assert.assertEquals(b[0], dbFile.getLatestVersion().getData()[0]);
    }

    @Test
    public void testPersistWithVersionCascade(){
        File file = new File("file", null);
        FileVersion version = new FileVersion(new byte[]{2, 3, 4},null);
        file.getVersionsList().add(version);
        em.persist(file);

        FileVersion dbVersion=em.createQuery("select v from FileVersion v",FileVersion.class).getSingleResult();
        File dbFile = em.createQuery("select f from File f", File.class).getSingleResult();
        Assert.assertEquals("file", dbFile.getName());
        byte[] b = new byte[]{2};
        Assert.assertEquals(b[0],dbVersion.getData()[0]);
        Assert.assertEquals(b[0],dbFile.getVersionsList().get(0).getData()[0]);
        Assert.assertEquals(b[0],dbFile.getLatestVersion().getData()[0]);
    }

    @Test
    public void testSeparatelySaveFileAndVersionThenStoreVersionIdInList(){
        File file = new File("file", null);
        em.persist(file);
        FileVersion version = new FileVersion(new byte[]{2, 3, 4},null);
        version.setRootFileId(file.getId());
        em.persist(version);

        FileVersion dbVersion=em.createQuery("select v from FileVersion v",FileVersion.class).getSingleResult();
        File dbFileByVersion=em.find(File.class,dbVersion.getRootFileId());


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
