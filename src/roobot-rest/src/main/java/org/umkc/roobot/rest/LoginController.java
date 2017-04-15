package org.umkc.roobot.rest;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.umkc.roobot.message.LoginData;
import org.umkc.roobot.message.SimpleErrorData;
import org.umkc.roobot.model.User;
import org.umkc.roobot.mongo.MongoHelper;

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
    
    //TODO - Check that password???
    User user = MongoHelper.getUser(loginData.getUserName());
    if (user == null)
      return new SimpleErrorData("No User Found", "I could not find a record for " + loginData.getUserName());

    return user;
  }
}
