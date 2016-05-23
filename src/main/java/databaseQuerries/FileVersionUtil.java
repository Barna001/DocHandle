package databaseQuerries;

import pojo.File;
import pojo.FileVersion;

import javax.persistence.EntityManager;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * Created by Barna on 2016.05.06..
 */
public class FileVersionUtil {

    public synchronized static File addVersionToFileAndPersistMerge(String fileId, FileVersion fileVersion, EntityManager em) {
        File dbFile = em.find(File.class, fileId);
        int calculatedVersion = dbFile.getLatestVersionNumber() + 1;
        fileVersion.setVersionNumber(calculatedVersion);
        fileVersion.setRootFileId(fileId);
        em.persist(fileVersion);//todo lehet hogy már egy előre lemergelt-et kellene átadni, de az meg csak lógna magában

        dbFile.setLatestVersionId(fileVersion.getId());
        dbFile.setLatestVersionNumber(calculatedVersion);
        em.merge(dbFile);//Here, this way the db remains in a consistent state
        return dbFile;
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
