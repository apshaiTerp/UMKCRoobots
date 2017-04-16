package org.umkc.roobot.rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.umkc.roobot.message.SimpleErrorData;
import org.umkc.roobot.model.CalEvent;
import org.umkc.roobot.mongo.MongoHelper;

/**
 * @author AC010168
 *
 */
@RestController
@RequestMapping("/calhint")
public class CalHintController {
  
  @RequestMapping(method=RequestMethod.GET, produces="application/json;charset=UTF-8")
  public Object getHint(@RequestParam(value="id", defaultValue="-1") long hintID) {
    System.out.println ("[DEBUG - " + new java.util.Date() + "] - Executing GET /calhint");
    
    if (hintID < 0)
      return new SimpleErrorData("Invalid Parameters", "No valid calevent ID was provided");

    CalEvent event = MongoHelper.getEventHint(hintID);
    return event;
  }
}
