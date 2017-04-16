package org.umkc.roobot.model;

import java.io.IOException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author AC010168
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Email {
  
  private long emailID;
  private String sender;
  private long senderID;
  private String recipient;
  private long recipientID;
  private String subject;
  private Date dateSent;
  private String messageBody;
  private String processedMessage;
  
  private List<CalEvent> calHints;
  
  public Email() {
    emailID          = -1L;
    sender           = null;
    senderID         = -1L;
    recipient        = null;
    recipientID      = -1L;
    subject          = null;
    dateSent         = null;
    messageBody      = null;
    processedMessage = null;
    calHints         = new LinkedList<CalEvent>();
  }
  
  public Email(String jsonString) {
    super();
    ObjectMapper mapper = new ObjectMapper();
    try {
      Email jsonData = mapper.readValue(jsonString, Email.class);
      emailID          = jsonData.emailID;
      sender           = jsonData.sender;
      senderID         = jsonData.senderID;
      recipient        = jsonData.recipient;
      recipientID      = jsonData.recipientID;
      subject          = jsonData.subject;
      dateSent         = jsonData.dateSent;
      messageBody      = jsonData.messageBody;
      processedMessage = jsonData.processedMessage;
      calHints         = jsonData.calHints;
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
   * @return the senderID
   */
  public long getSenderID() {
    return senderID;
  }

  /**
   * @param senderID the senderID to set
   */
  public void setSenderID(long senderID) {
    this.senderID = senderID;
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
   * @return the recipientID
   */
  public long getRecipientID() {
    return recipientID;
  }

  /**
   * @param recipientID the recipientID to set
   */
  public void setRecipientID(long recipientID) {
    this.recipientID = recipientID;
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
   * @return the messageBody
   */
  public String getMessageBody() {
    return messageBody;
  }

  /**
   * @param messageBody the messageBody to set
   */
  public void setMessageBody(String messageBody) {
    this.messageBody = messageBody;
  }

  /**
   * @return the processedMessage
   */
  public String getProcessedMessage() {
    return processedMessage;
  }

  /**
   * @param processedMessage the processedMessage to set
   */
  public void setProcessedMessage(String processedMessage) {
    this.processedMessage = processedMessage;
  }

  /**
   * @return the calHints
   */
  public List<CalEvent> getCalHints() {
    return calHints;
  }

  /**
   * @param calHints the calHints to set
   */
  public void setCalHints(List<CalEvent> calHints) {
    this.calHints = calHints;
  }

}
