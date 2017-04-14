package org.umkc.roobot;

import java.util.ArrayList;
import java.util.List;

import org.bson.BsonDocument;
import org.bson.BsonString;
import org.bson.Document;
import org.umkc.roobot.model.User;

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
    List<Document> foundDocument = (List<Document>) collection.find(doc).into(new ArrayList<Document>());
    
    User getUser = null;
    if (foundDocument.size() > 0) {
      for (Document curDoc : foundDocument) {
        getUser = new User(curDoc.toJson());
      }
    }
    return getUser;
  }
}
