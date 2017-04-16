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
public class InboxEmailHeader {
  private long emailID;
  private String sender;
  private Date dateSent;
  private String subject;
  
  public InboxEmailHeader() {
    emailID  = -1L;
    sender   = null;
    dateSent = null;
    subject  = null;
  }
  
  public InboxEmailHeader(long emailID, String sender, Date dateSent, String subject) {
    this.emailID  = emailID;
    this.sender   = sender;
    this.dateSent = dateSent;
    this.subject  = subject;
  }
  
  public InboxEmailHeader(String jsonString) {
    super();
    ObjectMapper mapper = new ObjectMapper();
    try {
      InboxEmailHeader jsonData = mapper.readValue(jsonString, InboxEmailHeader.class);
      emailID  = jsonData.emailID;
      sender   = jsonData.sender;
      dateSent = jsonData.dateSent;
      subject  = jsonData.subject;
    } catch (JsonParseException jpe) {
      jpe.printStackTrace();
    } catch (JsonMappingException jme) {
      jme.printStackTrace();
    } catch (IOException ioe) {
      ioe.printStackTrace();
    }
  }

  /**
   * @return the emailID
   */
  public long getEmailID() {
    return emailID;
  }

  /**
   * @param emailID the emailID to set
   */
  public void setEmailID(long emailID) {
    this.emailID = emailID;
  }

  /**
   * @return the sender
   */
  public String getSender() {
    return sender;
  }

  /**
   * @param sender the sender to set
   */
  public void setSender(String sender) {
    this.sender = sender;
  }

  /**
   * @return the dateSent
   */
  public Date getDateSent() {
    return dateSent;
  }

  /**
   * @param dateSent the dateSent to set
   */
  public void setDateSent(Date dateSent) {
    this.dateSent = dateSent;
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

}
