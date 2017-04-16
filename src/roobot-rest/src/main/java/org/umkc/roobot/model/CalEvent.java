package org.umkc.roobot.model;

import java.io.IOException;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author AC010168
 *
 */
public class CalEvent {
  
  private long eventID;
  private long origEmailID;
  private long hostUserID;
  private String date;
  private String time;
  private String meetWithName;
  private String meetWithAddress;
  private String subject;
  private String eventNotes;
  
  @JsonIgnoreProperties(ignoreUnknown = true)
  public CalEvent() {
    eventID         = -1L;
    origEmailID     = -1L;
    hostUserID      = -1L;
    date            = null;
    time            = null;
    meetWithName    = null;
    meetWithAddress = null;
    subject         = null;
    eventNotes      = null;
  }
  
  public CalEvent(String jsonString) {
    super();
    ObjectMapper mapper = new ObjectMapper();
    try {
      CalEvent jsonData = mapper.readValue(jsonString, CalEvent.class);
      eventID         = jsonData.eventID;
      origEmailID     = jsonData.origEmailID;
      hostUserID      = jsonData.hostUserID;
      date            = jsonData.date;
      time            = jsonData.time;
      meetWithName    = jsonData.meetWithName;
      meetWithAddress = jsonData.meetWithAddress;
      subject         = jsonData.subject;
      eventNotes      = jsonData.eventNotes;
    } catch (JsonParseException jpe) {
      jpe.printStackTrace();
    } catch (JsonMappingException jme) {
      jme.printStackTrace();
    } catch (IOException ioe) {
      ioe.printStackTrace();
    }
  }
  
  public void smoothDate() {
    //TODO
  }

  /**
   * @return the eventID
   */
  public long getEventID() {
    return eventID;
  }

  /**
   * @param eventID the eventID to set
   */
  public void setEventID(long eventID) {
    this.eventID = eventID;
  }

  /**
   * @return the origEmailID
   */
  public long getOrigEmailID() {
    return origEmailID;
  }

  /**
   * @param origEmailID the origEmailID to set
   */
  public void setOrigEmailID(long origEmailID) {
    this.origEmailID = origEmailID;
  }

  /**
   * @return the date
   */
  public String getDate() {
    return date;
  }

  /**
   * @param date the date to set
   */
  public void setDate(String date) {
    this.date = date;
  }

  /**
   * @return the time
   */
  public String getTime() {
    return time;
  }

  /**
   * @param time the time to set
   */
  public void setTime(String time) {
    this.time = time;
  }

  /**
   * @return the meetWithName
   */
  public String getMeetWithName() {
    return meetWithName;
  }

  /**
   * @param meetWithName the meetWithName to set
   */
  public void setMeetWithName(String meetWithName) {
    this.meetWithName = meetWithName;
  }

  /**
   * @return the meetWithAddress
   */
  public String getMeetWithAddress() {
    return meetWithAddress;
  }

  /**
   * @param meetWithAddress the meetWithAddress to set
   */
  public void setMeetWithAddress(String meetWithAddress) {
    this.meetWithAddress = meetWithAddress;
  }

  /**
   * @return the hostUserID
   */
  public long getHostUserID() {
    return hostUserID;
  }

  /**
   * @param hostUserID the hostUserID to set
   */
  public void setHostUserID(long hostUserID) {
    this.hostUserID = hostUserID;
  }

  /**
   * @return the subject
   */
  public String getSubject() {
    return subject;
  }

  /**
   * @param subject the subject to set
   */
  public void setSubject(String subject) {
    this.subject = subject;
  }

  /**
   * @return the eventNotes
   */
  public String getEventNotes() {
    return eventNotes;
  }

  /**
   * @param eventNotes the eventNotes to set
   */
  public void setEventNotes(String eventNotes) {
    this.eventNotes = eventNotes;
  }

}
