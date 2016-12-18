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
    public static void begin(EntityTransaction transaction){
        if(!transaction.isActive()){
            transaction.begin();
        }
    }
}
