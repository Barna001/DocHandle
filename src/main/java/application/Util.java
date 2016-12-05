package application;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.File;
import java.io.IOException;

/**
 * Created by Barna on 2016.11.26..
 */
public class Util {
    private static Config readConfig() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Config config = mapper.readValue(new File("config.json"), Config.class);
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
        return null;
    }

}
