package org.umkc.roobot.mongo;

import java.util.ArrayList;
import java.util.List;

import org.bson.BsonDocument;
import org.bson.BsonInt64;
import org.bson.BsonString;
import org.bson.Document;
import org.umkc.roobot.model.Email;
import org.umkc.roobot.model.User;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

/**
 * @author AC010168
 *
 */
public class MongoHelper {

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
    
    List<Document> foundDocument = (List<Document>) collection.find(doc).into(new ArrayList<Document>());
    
    System.out.println ("[DEBUG] Got some results: " + foundDocument.size());
    
    User getUser = null;
    if (foundDocument.size() > 0) {
      for (Document curDoc : foundDocument) {
        getUser = new User();
        getUser.setUserID(curDoc.getLong("userID"));
        getUser.setUserName(curDoc.getString("userName"));
        getUser.setFirstName(curDoc.getString("firstName"));
        getUser.setLastName(curDoc.getString("lastName"));
        getUser.setEmailAddress(curDoc.getString("emailAddress"));
      }
    }
    return getUser;
  }
  
  @SuppressWarnings({ "rawtypes", "unchecked" })
  public static Email getEmail(long emailID) {
    MongoCollection collection = mongoDB.getCollection("users");
    BsonDocument doc = new BsonDocument();
    doc.append("emailID", new BsonInt64(emailID));
    
    System.out.println ("[DEBUG] About to run email query");
    
    List<Document> foundDocument = (List<Document>) collection.find(doc).into(new ArrayList<Document>());
    
    System.out.println ("[DEBUG] Got some results: " + foundDocument.size());

    Email getEmail = null;
    if (foundDocument.size() > 0) {
      for (Document curDoc : foundDocument) {
        getEmail = new Email();
        getEmail.setEmailID(curDoc.getLong("emailID"));
        getEmail.setSender(curDoc.getString("sender"));
        if (curDoc.containsKey("senderID")) getEmail.setSenderID(curDoc.getLong("senderID"));
        getEmail.setRecipient(curDoc.getString("recipient"));
        if (curDoc.containsKey("recipientID")) getEmail.setRecipientID(curDoc.getLong("recipientID"));
        getEmail.setSubject(curDoc.getString("subject"));
        getEmail.setDateSent(curDoc.getDate("dateSent"));
        getEmail.setMessageBody(curDoc.getString("messageBody"));
        getEmail.setProcessedMessage(curDoc.getString("processedMessage"));
      }
    }
    
    return getEmail;
  }
}
