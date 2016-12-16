package application;

//import com.fasterxml.jackson.databind.ObjectMapper;
import org.codehaus.jackson.map.ObjectMapper;

import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.io.File;
import java.io.IOException;

/**
 * Created by Barna on 2016.11.26..
 */
public class Util {

    private static Config config = new Config();

    private static Config readConfig() throws IOException {
//        ObjectMapper mapper = new ObjectMapper();
//        Config config = mapper.readValue(new File("config.json"), Config.class);
//        return config;
        config.isModeInMongo=true;
        return config;
    }

    public static EntityManagerFactory getFactory() {
        try {
            Config config = readConfig();
            if (config.isModeInMongo) {
                return Persistence.createEntityManagerFactory("mongo_pu");
            } else {
                return Persistence.createEntityManagerFactory("mssql_pu");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Error with EntityManagerFactory loading, return null");
        return null;
    }

    public static EntityManagerFactory getTestFactory() {
        try {
            Config config = readConfig();
            if (config.isModeInMongo) {
                return Persistence.createEntityManagerFactory("test_pu");
            } else {
                return Persistence.createEntityManagerFactory("mssql_pu_test");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Error with EntityManagerFactory loading, return null");
        return null;
    }

    public static void begin(EntityTransaction transaction){
        if(!transaction.isActive()){
            transaction.begin();
        }
    }

    public static boolean isMongo() {
        Config config= null;
        try {
            config = readConfig();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return config.isModeInMongo;
    }
}
