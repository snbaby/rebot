/**
 * 
 */
package com.seadun.rebot.rest;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.seadun.rebot.constant.RebotExceptionConstants;
import com.seadun.rebot.entity.RebotException;
import com.seadun.rebot.entity.User;
import com.seadun.rebot.mapper.UserMapper;
import com.seadun.rebot.response.ResponseSuccessResult;

import lombok.extern.slf4j.Slf4j;


/**
 * @author Paul
 *
 */

@RestController
@RequestMapping("api/auth")
@Slf4j
public class AuthController {
	
	@Autowired
	private UserMapper userMapper;
	
	@RequestMapping(value = "login", method = RequestMethod.POST)
	@ResponseBody
    public ResponseEntity<ResponseSuccessResult> postAuthToken(HttpServletRequest request,@RequestBody User user) throws Exception {
		log.debug(">>>>>user login:{}",JSON.toJSONString(user));
		if(StringUtils.isBlank(user.getUserName())||StringUtils.isBlank(user.getUserPassword())) {
			throw new RebotException(RebotExceptionConstants.USER_VALID_FAILD_CODE,
					RebotExceptionConstants.USER_VALID_FAILD_MESSAGE,
					RebotExceptionConstants.USER_VALID_FAILD_HTTP_STATUS);
		}
		List<User> userList = userMapper.select(user);
		if(userList.size()==0) {
			throw new RebotException(RebotExceptionConstants.USER_VALID_FAILD_CODE,
					RebotExceptionConstants.USER_VALID_FAILD_MESSAGE,
					RebotExceptionConstants.USER_VALID_FAILD_HTTP_STATUS);
		}else if(userList.size()>1){
			throw new RebotException(RebotExceptionConstants.INTERNAL_SERVER_ERROR_CODE,
					RebotExceptionConstants.INTERNAL_SERVER_ERROR_MESSAGE,
					RebotExceptionConstants.INTERNAL_SERVER_ERROR_HTTP_STATUS);
		}else {
			request.getSession().setAttribute("isLogin", "yes");// 登录成功
			request.getSession().setAttribute("username", user.getUserName());// 登录成功
			request.getSession().setMaxInactiveInterval(30 * 60);
		}
		
		ResponseSuccessResult responseResult = new ResponseSuccessResult(HttpStatus.OK.value(), "success");
		return new ResponseEntity<>(responseResult, HttpStatus.OK);
    }
	
	@GetMapping(value = "logout")
	@ResponseBody
    public ResponseEntity<ResponseSuccessResult> postAuthToken(HttpServletRequest request) throws Exception {
		log.debug(">>>>>user:{} ,logout",request.getSession().getAttribute("username"));
		request.getSession().invalidate();
		
		ResponseSuccessResult responseResult = new ResponseSuccessResult(HttpStatus.OK.value(), "success");
		return new ResponseEntity<>(responseResult, HttpStatus.OK);
    }
}
