package service;

import databaseQuerries.FileVersionUtil;
import pojo.File;
import pojo.FileVersion;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

/**
 * Created by BB on 2016.05.22..
 */
public class FileService {
    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("mongo_pu");
    private static EntityManager em = emf.createEntityManager();

    public static File getFileById(String id) {
        return em.find(File.class, id);
    }

    public static List<File> getFilesByName(String name) {
        String query = "select f from File f where f.name=:name";
        return em.createQuery(query, File.class).setParameter("name", name).getResultList();
    }

    public static List<File> getAllFiles() {
        String query = "select f from File f";
        return em.createQuery(query, File.class).getResultList();
    }

    public static void addFile(File file) {
        em.persist(file);
    }

    public static void addFileWithVersion(File file, FileVersion version) {
        em.persist(file);
        version.setRootFileId(file.getId());
        FileVersionUtil.addVersionToFileAndPersistMerge(version, em);
    }

    public static void addVersionToFile(FileVersion version) {
        FileVersionUtil.addVersionToFileAndPersistMerge(version, em);
    }

    public static FileVersion getLatestVersion(String fileId) {
        File file = em.find(File.class, fileId);
        String versionId = file.getLatestVersionId();
        return em.find(FileVersion.class, versionId);
    }

    public static List<FileVersion> getAllVersionsForFileId(String fileId) {
        return em.createQuery("select v from FileVersion v where v.rootFileId=:id", FileVersion.class)
                .setParameter("id", fileId).getResultList();
    }

    public static void deleteAll() {
        String query = "delete from File";
        String queryVersion = "delete from FileVersion";
        em.createQuery(query).executeUpdate();
        em.createQuery(queryVersion).executeUpdate();
    }

    public static void closeAll() {
        em.close();
        emf.close();
    }
}
