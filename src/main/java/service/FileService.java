package service;

import application.Util;
import databaseQuerries.FileVersionUtil;
import org.apache.commons.io.IOUtils;
import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import pojo.File;
import pojo.FileVersion;

import javax.persistence.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Created by BB on 2016.05.22..
 */
public class FileService {
    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("mongo_pu");
    private static EntityManager em = emf.createEntityManager();
    private static EntityTransaction transaction = em.getTransaction();

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
        Util.begin(transaction);
        em.persist(file);
        transaction.commit();
    }

    public static void addFileWithVersion(File file, FileVersion version) {
        Util.begin(transaction);
        em.persist(file);
        version.setRootFileId(file.getId());
        FileVersionUtil.addVersionToFileAndPersistMerge(version, em);
        transaction.commit();
    }

    public static void addVersionToFile(FileVersion version) {
        Util.begin(transaction);
        FileVersionUtil.addVersionToFileAndPersistMerge(version, em);
        transaction.commit();
    }

    public static FileVersion getLatestVersion(String fileId) {
        File file;
        file = em.find(File.class, fileId);
        return em.find(FileVersion.class, file.getLatestVersionId());
    }

    public static List<FileVersion> getAllVersionsForFileId(String fileId) {

        return em.createQuery("select v from FileVersion v where v.rootFileId=:id", FileVersion.class)
                .setParameter("id", fileId).getResultList();

    }

    public void delete(String id) {
        Util.begin(transaction);
        String query = "delete from File f where f.id=:id";
        String queryVersion = "delete from FileVersion fv where fv.rootFileId=:rootId";
        em.createQuery(query).setParameter("id", id).executeUpdate();
        em.createQuery(queryVersion).setParameter("rootId", id).executeUpdate();
        transaction.commit();
    }

    public static void closeAll() {
        em.close();
        emf.close();
    }


    public static void addNewVersionToFile(InputStream fileData, FormDataContentDisposition fileDetails, FormDataBodyPart args, String rootId) {
        FileVersion version = new FileVersion(rootId, null);
        version.setFileType(args.getMediaType().toString());
        try {
            byte[] byteData = IOUtils.toByteArray(fileData);
            version.setData(byteData);
        } catch (IOException e) {
            e.printStackTrace();
        }
        addVersionToFile(version);
        try {
            fileData.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
