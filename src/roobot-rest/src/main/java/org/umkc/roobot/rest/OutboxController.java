package org.umkc.roobot.rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.umkc.roobot.message.SimpleErrorData;
import org.umkc.roobot.model.OutboxList;
import org.umkc.roobot.mongo.MongoHelper;

/**
 * This method supports the Outbox data.  It should only support GET.
 * @author AC010168
 *
 */
@RestController
@RequestMapping("/outbox")
public class OutboxController {

  @RequestMapping(method=RequestMethod.GET, produces="application/json;charset=UTF-8")
  public Object getInbox(@RequestParam(value="id", defaultValue="-1") long userID,
                         @RequestParam(value="limit", defaultValue="20") int limit) {
    if (userID < 0)
      return new SimpleErrorData("Invalid Parameters", "No valid user ID was provided");
   
    OutboxList list = MongoHelper.getUserOutbox(userID, limit);
    return list;
  }
}
