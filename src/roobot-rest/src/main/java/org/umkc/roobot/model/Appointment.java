package org.umkc.roobot.model;

import java.io.IOException;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author AC010168
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Appointment {
  
  private long apptID;
  /** The user to whom this appointment is assigned */
  private long userID;
  private String subject;
  private String notes;
  private Date startTime;
  private Date endTime;
  
  public Appointment() {
    apptID    = -1L;
    userID    = -1L;
    subject   = null;
    notes     = null;
    startTime = null;
    endTime   = null;
  }
  
  public Appointment(String jsonString) {
    super();
    ObjectMapper mapper = new ObjectMapper();
    try {
      Appointment jsonData = mapper.readValue(jsonString, Appointment.class);
      apptID    = jsonData.apptID;
      userID    = jsonData.userID;
      subject   = jsonData.subject;
      notes     = jsonData.notes;
      startTime = jsonData.startTime;
      endTime   = jsonData.endTime;
    } catch (JsonParseException jpe) {
      jpe.printStackTrace();
    } catch (JsonMappingException jme) {
      jme.printStackTrace();
    } catch (IOException ioe) {
      ioe.printStackTrace();
    }
  }

  /**
   * @return the apptID
   */
  public long getApptID() {
    return apptID;
  }

  /**
   * @param apptID the apptID to set
   */
  public void setApptID(long apptID) {
    this.apptID = apptID;
  }

  /**
   * @return the userID
   */
  public long getUserID() {
    return userID;
  }

  /**
   * @param userID the userID to set
   */
  public void setUserID(long userID) {
    this.userID = userID;
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
   * @return the notes
   */
  public String getNotes() {
    return notes;
  }

  /**
   * @param notes the notes to set
   */
  public void setNotes(String notes) {
    this.notes = notes;
  }

  /**
   * @return the startTime
   */
  public Date getStartTime() {
    return startTime;
  }

  /**
   * @param startTime the startTime to set
   */
  public void setStartTime(Date startTime) {
    this.startTime = startTime;
  }

  /**
   * @return the endTime
   */
  public Date getEndTime() {
    return endTime;
  }

  /**
   * @param endTime the endTime to set
   */
  public void setEndTime(Date endTime) {
    this.endTime = endTime;
  }
}
