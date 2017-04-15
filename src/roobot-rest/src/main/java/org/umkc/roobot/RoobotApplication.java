package org.umkc.roobot;

import javax.annotation.PreDestroy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.umkc.roobot.mongo.MongoHelper;

import com.mongodb.MongoClient;

/**
 * @author AC010168
 *
 */
@SpringBootApplication
public class RoobotApplication {
  
  //private final static String MONGO_HOST = "192.168.1.177";
  private final static String MONGO_HOST = "localhost";
  private final static int    MONGO_PORT = 27017;

  public static MongoClient mongo;
  
  public static void main(String[] args) {
    try {
      System.out.println ("[DEBUG] Trying to connect to mongoDB at " + MONGO_HOST + ":" + 
          MONGO_PORT + "...");
      mongo = new MongoClient(MONGO_HOST, MONGO_PORT);
      System.out.println ("[INFO] Mongo Connection Established.");
      
      MongoHelper.init(mongo);
      
    } catch (Throwable t) {
      System.out.println ("I had problems opening the Mongo Client");
      t.printStackTrace();
      System.exit(1);
    }
    
    SpringApplication.run(RoobotApplication.class, args);
  }
  
  @PreDestroy
  public static void shutdownHook() {
    System.out.println ("[DEBUG] Shut it down!");
    try {
      mongo.close();
    } catch (Throwable t) { /** Ignore close errors */ }
  }

}
