package org.umkc.roobot.rest;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.umkc.roobot.message.LoginData;
import org.umkc.roobot.message.SimpleErrorData;
import org.umkc.roobot.message.SimpleMessageData;

/**
 * This class should process a login request.  This Endpoint should only support POST.
 * 
 * @author AC010168
 *
 */
@RestController
@RequestMapping("/login")
public class LoginController {

  @RequestMapping(method=RequestMethod.POST, consumes = "application/json;charset=UTF-8", produces="application/json;charset=UTF-8")
  public Object doLogin(@RequestBody LoginData loginData) {
    //Check basic data integrity
    if (loginData == null)
      return new SimpleErrorData("Login Error", "There was no valid login data provided");
    if (loginData.getUserName() == null)
      return new SimpleErrorData("Login Error", "There was no login user name provided");
    if (loginData.getPassword() == null)
      return new SimpleErrorData("Login Error", "There was no login password provided");
    
    //TODO - Need to Return Some User parameters after getting the object
    
    return new SimpleMessageData("SUCCESS", "I don't really work, but I don't fail either...");
  }
}
