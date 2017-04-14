package org.umkc.roobot;

import java.util.ArrayList;
import java.util.List;

import org.bson.BsonDocument;
import org.bson.BsonString;
import org.bson.Document;
import org.umkc.roobot.model.User;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

/**
 * @author AC010168
 *
 */
public class MongoWriter {

  private final static String DB_NAME = "roobotdb";
  
  private static MongoDatabase mongoDB;
  
  public static void init(MongoClient mongo) {
    mongoDB = mongo.getDatabase(DB_NAME);
  }
  
  @SuppressWarnings({ "rawtypes", "unchecked" })
  public static User getUser(String userName) {
    MongoCollection collection = mongoDB.getCollection("users");
    BsonDocument doc = new BsonDocument();
    doc.append("userName", new BsonString(userName));
    
    System.out.println ("[DEBUG] About to run person query");
    
    List<BasicDBObject> foundDocument = (List<BasicDBObject>) collection.find(doc).into(new ArrayList<BasicDBObject>());
    
    System.out.println ("[DEBUG] Got some results: " + foundDocument.size());
    
    User getUser = null;
    if (foundDocument.size() > 0) {
      for (BasicDBObject curDoc : foundDocument) {
        getUser = new User(curDoc.toJson());
      }
    }
    return getUser;
  }
}
