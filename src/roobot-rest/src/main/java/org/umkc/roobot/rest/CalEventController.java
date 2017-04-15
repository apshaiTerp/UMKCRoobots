package org.umkc.roobot.rest;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.umkc.roobot.message.SimpleErrorData;
import org.umkc.roobot.message.SimpleMessageData;
import org.umkc.roobot.model.CalEvent;
import org.umkc.roobot.mongo.MongoHelper;

/**
 * @author AC010168
 *
 */
@RestController
@RequestMapping("/calhint")
public class CalEventController {

  @RequestMapping(method=RequestMethod.GET, produces="application/json;charset=UTF-8")
  public Object getHint(@RequestParam(value="userid", defaultValue="-1") long userID,
                        @RequestParam(value="eventid", defaultValue="-1") long eventID) {
    if (userID < 0 && eventID < 0)
      return new SimpleErrorData("Invalid Parameters", "No valid ID value was provided");
    
    //TODO, different based on which request was received
    CalEvent event = null;
    if (eventID > 0)
      event = MongoHelper.getEvent(eventID);
    
    if (userID > 0)
      return new SimpleErrorData("Unsupported Operation", "I haven't gotten around to this yet.  Chill...");
    return event;
  }
  
  @RequestMapping(method=RequestMethod.POST, consumes = "application/json;charset=UTF-8", produces="application/json;charset=UTF-8")
  public Object postEvent(@RequestBody CalEvent event) {
    if (event == null)
      return new SimpleErrorData("POST Error", "There was no valid event data provided");
      
    MongoHelper.writeCalEvent(event);
    
    return new SimpleMessageData("Event Scheduled", "This event has been scheduled.");
  }


}
