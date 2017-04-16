package org.umkc.roobot.rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.umkc.roobot.message.SimpleErrorData;
import org.umkc.roobot.model.InboxList;
import org.umkc.roobot.mongo.MongoHelper;

/**
 * This request should get the contents of an inbox.  It should only support GET.
 * @author AC010168
 *
 */
@RestController
@RequestMapping("/inbox")
public class InboxController {
  
  @RequestMapping(method=RequestMethod.GET, produces="application/json;charset=UTF-8")
  public Object getInbox(@RequestParam(value="id", defaultValue="-1") long userID,
                         @RequestParam(value="limit", defaultValue="20") int limit) {
    System.out.println ("[DEBUG - " + new java.util.Date() + "] - Executing GET /inbox");

    if (userID < 0)
      return new SimpleErrorData("Invalid Parameters", "No valid user ID was provided");
   
    InboxList list = MongoHelper.getUserInbox(userID, limit);
    return list;
  }

}
