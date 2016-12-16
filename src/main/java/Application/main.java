package application;

import databaseQuerries.FileVersionUtil;
import javafx.scene.paint.Stop;
import jersey.repackaged.com.google.common.base.Stopwatch;
import pojo.*;
import pojo.Access;

import javax.management.InvalidAttributeValueException;
import javax.persistence.*;
import javax.sound.midi.Soundbank;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by Barna on 2016.04.28..
 */
public class main {
    public static void main(String[] args) throws IOException, InvalidAttributeValueException {

        EntityManagerFactory emf = Util.getFactory();
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
////      Upload
//        Util.begin(transaction);
//        em.createQuery("delete from FileVersion").executeUpdate();
//        transaction.commit();
//
//        byte[] data;
//        //The names are in KB
//        ArrayList<String> testList = new ArrayList<>();
//        testList.add("d:/Munka/7_felev/szakdoga/teszt/1.txt");
//        testList.add("d:/Munka/7_felev/szakdoga/teszt/20.docx");
//        testList.add("d:/Munka/7_felev/szakdoga/teszt/1397.docx");
//        testList.add("d:/Munka/7_felev/szakdoga/teszt/259227.mkv");
//        for (String testFile : testList) {
//            data = FileVersionUtil.createBinaryData(testFile);
//            FileVersion fileVersion = new FileVersion(1, data);
//
//            Stopwatch stopwatch = Stopwatch.createStarted();
//            Util.begin(transaction);
//            em.persist(fileVersion);
//            transaction.commit();
//            stopwatch.stop();
//
//            System.out.println("Time for saving " + data.length + " byte took: " + stopwatch.elapsed(TimeUnit.MILLISECONDS) + " ms");
//            System.gc();
//        }


        //Download


        ArrayList<Integer> ids = new ArrayList<>();
        ids.add(89);
        ids.add(90);
        ids.add(91);
        ids.add(92);
        for (Integer id : ids) {
            Stopwatch stopwatch = Stopwatch.createStarted();
            byte[] data = em.find(FileVersion.class,id).getData();
            System.out.println("Time for downloading " + data.length + " byte took: " + stopwatch.elapsed(TimeUnit.MILLISECONDS) + " ms");
            System.gc();
        }

        em.close();
        emf.close();
    }

    private static void deleteAll(EntityManager em) {
        Query delete = em.createNamedQuery("delete from User");
        delete.executeUpdate();
        Query delete1 = em.createNamedQuery("delete from UserGroup");
        delete1.executeUpdate();
        Query delete2 = em.createNamedQuery("delete from PermissionSubject");
        delete2.executeUpdate();
        Query delete3 = em.createNamedQuery("delete from Document");
        delete3.executeUpdate();
        Query delete4 = em.createNamedQuery("delete from DocumentGroup");
        delete4.executeUpdate();
        Query delete5 = em.createNamedQuery("delete from Access");
        delete5.executeUpdate();
        System.out.println("Deleted successfully");
    }
}
