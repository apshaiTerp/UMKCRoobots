package org.umkc.roobot.parser;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.umkc.roobot.model.CalEvent;
import org.umkc.roobot.model.Email;
import org.umkc.roobot.model.User;
import org.umkc.roobot.mongo.MongoHelper;

import com.joestelmach.natty.DateGroup;
import com.joestelmach.natty.Parser;

/**
 * @author AC010168
 *
 */
public class NattyParser {
  
  private Parser parser;
  private String resultString;
  private List<CalEvent> calHints;
  
  private static SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
  private static SimpleDateFormat timeFormatter = new SimpleDateFormat("HH:mm:ss");
  
  public NattyParser() {
    parser = new Parser();
    resultString = "";
    calHints = new LinkedList<CalEvent>();
  }
  /**
   * High level overview because I'm tired:
   * <ul><li>Run the parsing algorithm against the email sent date</li>
   * <li>For each hit:  <ul><li>Grab the metadata.</li>
   * <li>Create the CalEvent object</li>
   * <li>Smooth the date results</li></ul>
   * <li>Add our HTML tags around the hit</li>
   * <li>Check if we need to still keep going</li></ul>
   * 
   * @param email
   * @param invitee
   */
  public void parseEmailBody(Email email, User invitee) {
    String textToParse = email.getMessageBody();
    
    List<DateGroup> groups = parser.parse(textToParse, email.getDateSent());
    if (groups.size() == 0) {
      resultString = email.getMessageBody();
      return;
    }
    
    //For the first group in the block
    while (groups.size() > 0) {
      DateGroup group = groups.get(0);

      //set the meeting details
      CalEvent curEvent = new CalEvent();
      
      curEvent.setEventID(MongoHelper.getNextEventID());
      curEvent.setHostUserID(email.getRecipientID());
      curEvent.setOrigEmailID(email.getEmailID());
      curEvent.setMeetWithAddress(invitee.getEmailAddress());
      curEvent.setMeetWithName(invitee.getLastName() + ", " + invitee.getFirstName());
      curEvent.setSubject(email.getSubject());
      curEvent.setEventNotes("");
      
      //Now we just need to set our date and time, as well as the modified email body
      Date suggestDate = group.getDates().get(0);
      String dateString = dateFormatter.format(suggestDate);
      String timeString = "";
      
      //If the date was just guessed at, smooth it out
      if (group.isTimeInferred()) {
        //Try manually parsing out Time of Day with smoothing
        Calendar tempCalendar = Calendar.getInstance();
        tempCalendar.setTime(suggestDate);
        int hours = tempCalendar.get(Calendar.HOUR_OF_DAY);
        int minutes = tempCalendar.get(Calendar.MINUTE);
        
        //Smooth out minutes
        if (minutes < 15)
          minutes = 0;
        else if (minutes > 15 && minutes < 45)
          minutes = 30;
        else {
          //We'd want to round up to the next hour.  Check if we can do that today...
          if (hours < 23) {
            hours++;
            minutes = 0;
          } else minutes = 30;
        }
        
        if (hours < 10)   timeString += "0" + hours + ":";
        else              timeString += "" + hours + ":";
        if (minutes < 10) timeString += "0" + minutes + ":00";
        else              timeString += "" + minutes + ":00";
      } else {
        timeString = timeFormatter.format(suggestDate);
      }
      curEvent.setDate(dateString);
      curEvent.setTime(timeString);
      
      //curEvent complete, add it to the list
      calHints.add(curEvent);
      
      String matchingValue = group.getText();
      String prefix = group.getPrefix(textToParse.length());
      String suffix = group.getSuffix(textToParse.length());

      resultString += prefix + "<a href=\"#\" style=\"text-decoration:none;display:block,inline;\">" + 
          "<span style=\"white-space:nowrap\" onclick=\"mycalendarfunc(" + curEvent.getEventID() + ")\">" + matchingValue + "</span></a>";
      
      //If this is the last group, grab the suffix and finish the sentence.
      if (groups.size() == 1) {
        resultString += suffix;
        break;
      } else {
        //'Iterate' through the string, rather than deal with subsection madness
        textToParse = suffix;
        groups = parser.parse(textToParse);
      }
    }

  }
  /**
   * @return the resultString
   */
  public String getResultString() {
    return resultString;
  }
  /**
   * @return the calHints
   */
  public List<CalEvent> getCalHints() {
    return calHints;
  }

}
