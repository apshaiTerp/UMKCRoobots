package org.umkc.roobot.rest;

import java.util.Date;
import java.util.List;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.umkc.roobot.message.SimpleErrorData;
import org.umkc.roobot.message.SimpleMessageData;
import org.umkc.roobot.model.Email;
import org.umkc.roobot.model.User;
import org.umkc.roobot.mongo.MongoHelper;


/**
 * This method supports single email operations.  It should support GET, POST, and DELETE.
 * 
 * @author AC010168
 *
 */
@RestController
@RequestMapping("/email")
public class EmailController {

  @RequestMapping(method=RequestMethod.GET, produces="application/json;charset=UTF-8")
  public Object getEmail(@RequestParam(value="id", defaultValue="-1") long emailID) {
    if (emailID < 0)
      return new SimpleErrorData("Invalid Parameters", "No valid emailid was provided");
    
    Email email = MongoHelper.getEmail(emailID);
    if (email == null)
      return new SimpleErrorData("No Email Found", "I could not find a record for email id " + emailID);

    return email;
  }

  @RequestMapping(method=RequestMethod.POST, consumes = "application/json;charset=UTF-8", produces="application/json;charset=UTF-8")
  public Object postEmail(@RequestBody Email email) {
    if (email == null)
      return new SimpleErrorData("Send Error", "There was no valid email data provided");

    if (email.getSender() == null)
      return new SimpleErrorData("Send Error", "There was no sender address provided");
    if (email.getRecipient() == null)
      return new SimpleErrorData("Send Error", "There was no recipient address provided");
    if (email.getSubject() == null)
      return new SimpleErrorData("Send Error", "There was no subject line provided");
    if (email.getMessageBody() == null)
      return new SimpleErrorData("Send Error", "There was no message text provided");

    //Start trying to fill out other fields
    if (email.getDateSent() == null)
      email.setDateSent(new Date());
    
    //Try to find the userIDs for the sender/recipient
    List<User> allUsers = MongoHelper.getAllUsers();
    
    if (email.getSenderID() < 0 || email.getRecipientID() < 0) {
      for (User curUser : allUsers) {
        if (curUser.getEmailAddress().equalsIgnoreCase(email.getSender()))
          email.setSenderID(curUser.getUserID());
        if (curUser.getEmailAddress().equalsIgnoreCase(email.getRecipient()))
          email.setSenderID(curUser.getUserID());
      }
    }
    MongoHelper.writeEmail(email);
    
    return new SimpleMessageData("Email Delivered", "This email has been received.");
  }

}
