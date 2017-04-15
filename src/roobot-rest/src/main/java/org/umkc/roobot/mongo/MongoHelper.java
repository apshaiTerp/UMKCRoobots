package org.umkc.roobot.mongo;

import java.util.ArrayList;
import java.util.List;

import org.bson.BsonDateTime;
import org.bson.BsonDocument;
import org.bson.BsonInt64;
import org.bson.BsonString;
import org.bson.Document;
import org.umkc.roobot.model.Email;
import org.umkc.roobot.model.InboxEmailHeader;
import org.umkc.roobot.model.InboxList;
import org.umkc.roobot.model.OutboxEmailHeader;
import org.umkc.roobot.model.OutboxList;
import org.umkc.roobot.model.User;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

/**
 * @author AC010168
 *
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class MongoHelper {

  private final static String DB_NAME = "roobotdb";
  
  private static MongoDatabase mongoDB;
  
  private static long maxEmailID = -1L;
  
  public static void init(MongoClient mongo) {
    mongoDB = mongo.getDatabase(DB_NAME);
  }
  
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
  
  public static List<User> getAllUsers() {
    MongoCollection collection = mongoDB.getCollection("users");
    System.out.println ("[DEBUG] About to run get all users query");
    
    List<Document> foundDocument = (List<Document>) collection.find().into(new ArrayList<Document>());
    
    System.out.println ("[DEBUG] Got some results: " + foundDocument.size());
    
    List<User> allUsers = new ArrayList<User>(foundDocument.size());
    if (foundDocument.size() > 0) {
      for (Document curDoc : foundDocument) {
        User getUser = new User();
        getUser.setUserID(curDoc.getLong("userID"));
        getUser.setUserName(curDoc.getString("userName"));
        getUser.setFirstName(curDoc.getString("firstName"));
        getUser.setLastName(curDoc.getString("lastName"));
        getUser.setEmailAddress(curDoc.getString("emailAddress"));
        allUsers.add(getUser);
      }
    }
    return allUsers;
  }
  
  public static Email getEmail(long emailID) {
    MongoCollection collection = mongoDB.getCollection("emails");
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
  
  public static void writeEmail(Email email) {
    MongoCollection collection = mongoDB.getCollection("emails");
    Document doc = new Document();
    
    if (maxEmailID < 0)
      maxEmailID = getMaxEmailID();
    maxEmailID++;
    
    doc.append("emailID", new BsonInt64(maxEmailID));
    doc.append("sender", new BsonString(email.getSender()));
    if (email.getSenderID() > 0) doc.append("senderID", new BsonInt64(email.getSenderID()));
    doc.append("recipient", new BsonString(email.getRecipient()));
    if (email.getRecipientID() > 0) doc.append("recipientID", new BsonInt64(email.getRecipientID()));
    doc.append("subject", new BsonString(email.getSubject()));
    doc.append("dateSent", new BsonDateTime(email.getDateSent().getTime()));
    doc.append("messageBody", new BsonString(email.getMessageBody()));
    //Deliberately omit processed message at this time.
    collection.insertOne(doc);
  }
  
  public static void deleteEmail(long emailID) {
    MongoCollection collection = mongoDB.getCollection("emails");
    Document doc = new Document();
    doc.append("emailID", new BsonInt64(emailID));
    
    collection.deleteOne(doc);
  }
  
  public static long getMaxEmailID() {
    MongoCollection collection = mongoDB.getCollection("emails");
    FindIterable iter = collection.find().sort(new BasicDBObject("emailID", -1)).limit(1);
    Document doc = (Document)iter.first();
    long emailID = doc.getLong("emailID");
    return emailID;
  }
  
  public static InboxList getUserInbox(long userID, int limit) {
    MongoCollection collection = mongoDB.getCollection("emails");
    BsonDocument doc = new BsonDocument();
    doc.append("recipientID", new BsonInt64(userID));

    FindIterable iter = collection.find(doc).sort(new BasicDBObject("dateSent", -1)).limit(limit);
    MongoCursor cursor = iter.iterator();
    InboxList list = new InboxList();
    while (cursor.hasNext()) {
      Document curDoc = (Document)cursor.next();
      InboxEmailHeader getEmail = new InboxEmailHeader();
      getEmail.setEmailID(curDoc.getLong("emailID"));
      getEmail.setSender(curDoc.getString("sender"));
      getEmail.setSubject(curDoc.getString("subject"));
      getEmail.setDateSent(curDoc.getDate("dateSent"));
      list.addEmailHeader(getEmail);
    }
    return list;
  }
  
  public static OutboxList getUserOutbox(long userID, int limit) {
    MongoCollection collection = mongoDB.getCollection("emails");
    BsonDocument doc = new BsonDocument();
    doc.append("senderID", new BsonInt64(userID));

    FindIterable iter = collection.find(doc).sort(new BasicDBObject("dateSent", -1)).limit(limit);
    MongoCursor cursor = iter.iterator();
    OutboxList list = new OutboxList();
    while (cursor.hasNext()) {
      Document curDoc = (Document)cursor.next();
      OutboxEmailHeader getEmail = new OutboxEmailHeader();
      getEmail.setEmailID(curDoc.getLong("emailID"));
      getEmail.setRecipient(curDoc.getString("recipient"));
      getEmail.setSubject(curDoc.getString("subject"));
      getEmail.setDateSent(curDoc.getDate("dateSent"));
      list.addEmailHeader(getEmail);
    }
    return list;
  }
  
}
