package org.umkc.roobot.mongo;

import org.bson.Document;
import org.junit.Test;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

/**
 * @author AC010168
 *
 */
public class TestBsonGarbage {

  //private final static String MONGO_HOST = "192.168.1.177";
  private final static String MONGO_HOST = "localhost";
  private final static int    MONGO_PORT = 27017;

  public static MongoClient mongo;

  @Test
  public void testDocumentWriter() {
    mongo = new MongoClient(MONGO_HOST, MONGO_PORT);
    System.out.println ("[INFO] Mongo Connection Established.");

    MongoDatabase mongoDB = mongo.getDatabase("roobotdb");
    MongoCollection collection = mongoDB.getCollection("junk");
    
    BasicDBObject doc1 = new BasicDBObject();
    doc1.append("garbage", "A Garbage String");
    
    BasicDBObject doc2 = new BasicDBObject();
    doc2.append("garbage", "A Garbage String Also");
    
    Document doc3 = new Document();
    BasicDBList list = new BasicDBList();
    list.add(doc1);
    list.add(doc2);

    doc3.append("classify", "Parent Doc");
    doc3.append("allGarbage", list);
    
    collection.insertOne(doc3);
    
    mongo.close();
  }
}
