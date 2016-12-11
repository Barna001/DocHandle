package service;

import application.Util;
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
    private static EntityManagerFactory emf = Util.getFactory();
    private static EntityManager em = emf.createEntityManager();

    public static File getFileById(String id) {
        if (Util.isMongo()) {
            return em.find(File.class, id);
        } else {
            return em.find(File.class, Integer.valueOf(id));
        }
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
        File file;
        if (Util.isMongo()) {
            file = em.find(File.class, fileId);
        } else {
            file = em.find(File.class, Integer.valueOf(fileId));
        }
        int versionId = file.getLatestVersionId();
        return em.find(FileVersion.class, versionId);
    }

    public static List<FileVersion> getAllVersionsForFileId(String fileId) {
        if (Util.isMongo()) {
            return em.createQuery("select v from FileVersion v where v.rootFileId=:id", FileVersion.class)
                    .setParameter("id", fileId).getResultList();
        } else {
            return em.createQuery("select v from FileVersion v where v.rootFileId=:id", FileVersion.class)
                    .setParameter("id", Integer.valueOf(fileId)).getResultList();
        }
    }

    public void delete(String id) {
        String query = "delete from File f where f.id=:id";
        String queryVersion = "delete from FileVersion fv where fv.rootFileId=:rootId";
        if (Util.isMongo()) {
            em.createQuery(query).setParameter("id", id).executeUpdate();
            em.createQuery(queryVersion).setParameter("rootId", id).executeUpdate();
        } else {
            em.createQuery(query).setParameter("id", Integer.valueOf(id)).executeUpdate();
            em.createQuery(queryVersion).setParameter("rootId", Integer.valueOf(id)).executeUpdate();
        }
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
