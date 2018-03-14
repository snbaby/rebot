/**
 * 
 */
package com.seadun.rebot.rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.seadun.rebot.bean.UserInfo;


/**
 * @author Paul
 *
 */

@RestController
@RequestMapping("user")
public class UserController {
	
	@RequestMapping(value = "token", method = RequestMethod.POST)
	@ResponseBody
    public UserInfo createAuthenticationToken(UserInfo userInfo) throws Exception {
        System.out.println(userInfo);
        userInfo.setUsername("username");
        userInfo.setUsername("password");
        return userInfo;
    }
	
	@RequestMapping(value = "/info", method = RequestMethod.GET)
    public String getUserInfo() throws Exception {
        return "username";
    }
}
