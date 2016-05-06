import DatabaseQueries.FileVersionUtil;
import Pojo.Document;
import Pojo.File;
import Pojo.FileVersion;
import org.junit.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

/**
 * Created by Barna on 2016.05.06..
 */
public class FileAndFileVersionTest {
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
        Assert.assertEquals("file", file.getName());
        Assert.assertEquals("content", file.getRootDocument().getContent());
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
        FileVersion version = new FileVersion(new byte[]{2, 3, 4});
        em.persist(version);
        FileVersion dbVersion= em.createQuery("select v from FileVersion v",FileVersion.class).getSingleResult();
        byte[] b = new byte[]{2};
        Assert.assertEquals(b[0],dbVersion.getData()[0]);
    }

    @Test
    public void testPersistWithVersion() {
        File file = new File("file", null);
        em.persist(file);

        FileVersion version = new FileVersion(new byte[]{2, 3, 4});
        FileVersionUtil.addVersionToFileAndPersistMerge(file.getId(),version,em);

        FileVersion dbVersion=em.createQuery("select v from FileVersion v",FileVersion.class).getSingleResult();
        File dbFile = em.find(File.class,dbVersion.getRootFileId());
        Assert.assertEquals("file", dbFile.getName());
        byte[] b = new byte[]{2};
        Assert.assertEquals(dbFile.getId(),dbVersion.getRootFileId());
        Assert.assertEquals(1,dbVersion.getVersionNumber());
        Assert.assertEquals(b[0],dbVersion.getData()[0]);
    }

    @Test
    public void testAddSecondVersionsNumber(){
        File file = new File("file", null);
        FileVersion version = new FileVersion(new byte[]{2, 3, 4});
        FileVersion version2 = new FileVersion(new byte[]{5, 6, 7, 8});
        em.persist(file);

        FileVersionUtil.addVersionToFileAndPersistMerge(file.getId(),version,em);
        FileVersionUtil.addVersionToFileAndPersistMerge(file.getId(),version2,em);

        Assert.assertEquals(1,version.getVersionNumber());
        Assert.assertEquals(2,version2.getVersionNumber());
    }

    @Test
    public void testFindLatestVersionOfFile(){
        File file = new File("file", null);
        em.persist(file);

        FileVersion version = new FileVersion(new byte[]{2, 3, 4});
        FileVersion version2 = new FileVersion(new byte[]{5, 6, 7, 8});

        file=FileVersionUtil.addVersionToFileAndPersistMerge(file.getId(),version,em);
        file=FileVersionUtil.addVersionToFileAndPersistMerge(file.getId(),version2,em);

        List<FileVersion> dbVersions=em.createQuery("select v from FileVersion v where v.rootFileId=:id",FileVersion.class).setParameter("id",file.getId()).getResultList();
        Assert.assertEquals(2,dbVersions.size());
        byte[] b = new byte[]{5};
        Assert.assertEquals(b[0],em.find(FileVersion.class,file.getLatestVersionId()).getData()[0]);
    }

    @Test
    public void testFileSerializationToDb(){
        byte[] data = FileVersionUtil.createBinaryData("src/test/files/Kundera_GridFSTest.pdf");
        FileVersion version = new FileVersion(data);
        em.persist(version);

        FileVersion dbVersion=em.createQuery("select v from FileVersion v",FileVersion.class).getSingleResult();
        Assert.assertEquals(data.length,dbVersion.getData().length);
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
