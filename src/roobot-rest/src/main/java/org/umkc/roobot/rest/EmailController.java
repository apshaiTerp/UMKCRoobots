package org.umkc.roobot.rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.umkc.roobot.message.SimpleErrorData;
import org.umkc.roobot.model.Email;
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
  public Object getEmail(@RequestParam(value="emailid", defaultValue="-1") long emailID) {
    if (emailID < 0)
      return new SimpleErrorData("Invalid Parameters", "No valid emailid was provided");
    
    Email email = MongoHelper.getEmail(emailID);
    if (email == null)
      return new SimpleErrorData("No Email Found", "I could not find a record for emailid " + emailID);

    return email;
  }

}
