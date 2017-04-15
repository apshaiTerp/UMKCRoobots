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
public class OutboxEmailHeader {
  private long emailID;
  private String recipient;
  private Date dateSent;
  private String subject;
  
  public OutboxEmailHeader() {
    emailID   = -1L;
    recipient = null;
    dateSent  = null;
    subject   = null;
  }
  
  public OutboxEmailHeader(long emailID, String sender, Date dateSent, String subject) {
    this.emailID   = emailID;
    this.recipient = sender;
    this.dateSent  = dateSent;
    this.subject   = subject;
  }
  
  public OutboxEmailHeader(String jsonString) {
    super();
    ObjectMapper mapper = new ObjectMapper();
    try {
      OutboxEmailHeader jsonData = mapper.readValue(jsonString, OutboxEmailHeader.class);
      emailID   = jsonData.emailID;
      recipient = jsonData.recipient;
      dateSent  = jsonData.dateSent;
      subject   = jsonData.subject;
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
   * @return the recipient
   */
  public String getRecipient() {
    return recipient;
  }

  /**
   * @param recipient the recipient to set
   */
  public void setRecipient(String recipient) {
    this.recipient = recipient;
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
