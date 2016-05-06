package DatabaseQueries;

import Pojo.File;
import Pojo.FileVersion;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.swing.plaf.synth.SynthOptionPaneUI;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * Created by Barna on 2016.05.06..
 */
public class FileVersionUtil {

//    public static FileVersion getLatestVersionOfFile(String fileId, EntityManager em) {
//        List<FileVersion> dbVersions = em.createQuery("select v from FileVersion v where v.rootFileId=:id", FileVersion.class).setParameter("id", fileId).getResultList();
//
//        FileVersion latest = dbVersions.get(0);
//        for (FileVersion dbVersion : dbVersions) {
//            if (dbVersion.getVersionNumber() > latest.getVersionNumber()) {
//                latest = dbVersion;
//            }
//        }
//        return latest;
//    }

    public synchronized static File addVersionToFileAndPersistMerge(String fileId, FileVersion fileVersion, EntityManager em) {
//        List<FileVersion> dbVersions = em.createQuery("select v from FileVersion v where v.rootFileId=:id", FileVersion.class).setParameter("id", fileId).getResultList();
//        int calCulatedVersion = getNextVersionNumber(dbVersions);
        File dbFile = em.find(File.class, fileId);
        int calCulatedVersion = dbFile.getLatestVersionNumber() + 1;
        fileVersion.setVersionNumber(calCulatedVersion);
        fileVersion.setRootFileId(fileId);
        em.persist(fileVersion);//todo lehet hogy már egy előre lemergelt-et kellene átadni, de az meg csak lógna magában

        dbFile.setLatestVersionId(fileVersion.getId());
        dbFile.setLatestVersionNumber(calCulatedVersion);
        em.merge(dbFile);//Here, this way the db remains in a consistent state
        return dbFile;
    }

    private static int getNextVersionNumber(List<FileVersion> dbVersions) {
        int number = 1;//Default, this is the starting versionNumber, could be configurable
        if (dbVersions != null && !dbVersions.isEmpty()) {
            for (FileVersion dbVersion : dbVersions) {
                if (dbVersion.getVersionNumber() > number) {
                    number = dbVersion.getVersionNumber();
                }
            }
            number++;//Give the next version number
        }
        return number;
    }

    public static byte[] createBinaryData(String locationPath) {
        Path path = Paths.get(locationPath);
        byte[] data = null;
        try {
            data = Files.readAllBytes(path);
        } catch (IOException e) {
            System.out.println("Can not read the file");
        }

        return data;
    }
}
