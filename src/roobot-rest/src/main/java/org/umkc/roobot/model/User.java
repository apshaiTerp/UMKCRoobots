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
@JsonIgnoreProperties(ignoreUnknown = true)
public class User {
  
  private long   userID;
  private String userName;
  private String emailAddress;
  private String firstName;
  private String lastName;
  
  public User() {
    userID       = -1L;
    userName     = null;
    emailAddress = null;
    firstName    = null;
    lastName     = null;
  }
  
  public User(long userID, String userName, String emailAddress, String firstName, String lastName) {
    this.userID       = userID;
    this.userName     = userName;
    this.emailAddress = emailAddress;
    this.firstName    = firstName;
    this.lastName     = lastName;
  }
  
  public User(String jsonString) {
    super();
    ObjectMapper mapper = new ObjectMapper();
    try {
      User userData = mapper.readValue(jsonString, User.class);
      userID       = userData.userID;
      userName     = userData.userName;
      emailAddress = userData.emailAddress;
      firstName    = userData.firstName;
      lastName     = userData.lastName;
    } catch (JsonParseException jpe) {
      jpe.printStackTrace();
    } catch (JsonMappingException jme) {
      jme.printStackTrace();
    } catch (IOException ioe) {
      ioe.printStackTrace();
    }
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
   * @return the userName
   */
  public String getUserName() {
    return userName;
  }

  /**
   * @param userName the userName to set
   */
  public void setUserName(String userName) {
    this.userName = userName;
  }

  /**
   * @return the emailAddress
   */
  public String getEmailAddress() {
    return emailAddress;
  }

  /**
   * @param emailAddress the emailAddress to set
   */
  public void setEmailAddress(String emailAddress) {
    this.emailAddress = emailAddress;
  }

  /**
   * @return the firstName
   */
  public String getFirstName() {
    return firstName;
  }

  /**
   * @param firstName the firstName to set
   */
  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  /**
   * @return the lastName
   */
  public String getLastName() {
    return lastName;
  }

  /**
   * @param lastName the lastName to set
   */
  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

}
