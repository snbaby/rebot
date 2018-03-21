/**
 * 
 */
package com.seadun.rebot.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.seadun.rebot.entity.User;
import com.seadun.rebot.mapper.UserMapper;


/**
 * @author Paul
 *
 */

@RestController
@RequestMapping("auth")
public class AuthController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private UserMapper userMapper;
	
	@RequestMapping(value = "token", method = RequestMethod.POST)
	@ResponseBody
    public ResponseEntity<User> postAuthToken(@RequestBody User user) throws Exception {
        return new ResponseEntity<User>(user, HttpStatus.CREATED);
    }
}
