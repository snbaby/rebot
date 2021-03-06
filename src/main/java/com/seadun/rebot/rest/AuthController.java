/**
 * 
 */
package com.seadun.rebot.rest;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.seadun.rebot.constant.RebotExceptionConstants;
import com.seadun.rebot.entity.Log;
import com.seadun.rebot.entity.RebotException;
import com.seadun.rebot.entity.User;
import com.seadun.rebot.mapper.DepartmentMapper;
import com.seadun.rebot.mapper.LogMapper;
import com.seadun.rebot.mapper.RoleMapper;
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

	@Autowired
	private RoleMapper roleMapper;

	@Autowired
	private DepartmentMapper departmentMapper;

	@Autowired
	private LogMapper logMapper;

	@RequestMapping(value = "login", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<ResponseSuccessResult> postAuthToken(HttpServletRequest request, @RequestBody User user)
			throws Exception {
		log.debug(">>>>>user login:{}", JSON.toJSONString(user));
		if (StringUtils.isBlank(user.getUserName()) || StringUtils.isBlank(user.getUserPassword())) {
			throw new RebotException(RebotExceptionConstants.USER_VALID_FAILD_CODE,
					RebotExceptionConstants.USER_VALID_FAILD_MESSAGE,
					RebotExceptionConstants.USER_VALID_FAILD_HTTP_STATUS);
		}
		List<User> userList = userMapper.select(user);
		if (userList.size() == 0) {
			throw new RebotException(RebotExceptionConstants.USER_VALID_FAILD_CODE,
					RebotExceptionConstants.USER_VALID_FAILD_MESSAGE,
					RebotExceptionConstants.USER_VALID_FAILD_HTTP_STATUS);
		} else if (userList.size() > 1) {
			throw new RebotException(RebotExceptionConstants.INTERNAL_SERVER_ERROR_CODE,
					RebotExceptionConstants.INTERNAL_SERVER_ERROR_MESSAGE,
					RebotExceptionConstants.INTERNAL_SERVER_ERROR_HTTP_STATUS);
		} else {
			request.getSession().setAttribute("isLogin", "yes");// 登录成功
			request.getSession().setAttribute("username", user.getUserName());// 登录成功
			request.getSession().setAttribute("userId", userList.get(0).getId());// 登录成功
			request.getSession().setMaxInactiveInterval(4 * 60 * 60);
		}

		Log log = new Log();
		log.setId(UUID.randomUUID().toString());
		log.setUserId(request.getSession().getAttribute("userId").toString());
		log.setUserName(request.getSession().getAttribute("username").toString());
		log.setMessage("用户" + request.getSession().getAttribute("username").toString() + "登录系统");
		log.setCrtTime(new Date());
		logMapper.insert(log);

		JSONObject jsb = new JSONObject();
		jsb.put("username", user.getUserName());
		jsb.put("roleId", userList.get(0).getRoleId());
		jsb.put("userId", userList.get(0).getId());
		jsb.put("departmentId", userList.get(0).getDepartmentId());

		jsb.put("roleList", roleMapper.list());
		jsb.put("departmentList", departmentMapper.list());

		ResponseSuccessResult responseResult = new ResponseSuccessResult(HttpStatus.OK.value(), "success", jsb);
		return new ResponseEntity<>(responseResult, HttpStatus.OK);
	}

	@GetMapping(value = "logout")
	@ResponseBody
	public ResponseEntity<ResponseSuccessResult> postAuthToken(HttpServletRequest request) throws Exception {
		log.debug(">>>>>user:{} ,logout", request.getSession().getAttribute("username"));

		Log log = new Log();
		log.setId(UUID.randomUUID().toString());
		log.setUserId(request.getSession().getAttribute("userId").toString());
		log.setUserName(request.getSession().getAttribute("username").toString());
		log.setMessage("用户" + request.getSession().getAttribute("username").toString() + "退出系统");
		log.setCrtTime(new Date());
		logMapper.insert(log);

		request.getSession().invalidate();
		ResponseSuccessResult responseResult = new ResponseSuccessResult(HttpStatus.OK.value(), "success");
		return new ResponseEntity<>(responseResult, HttpStatus.OK);
	}

	@PostMapping(value = "change")
	@ResponseBody
	public ResponseEntity<ResponseSuccessResult> passwordChange(HttpServletRequest request, @RequestBody JSONObject jsb)
			throws Exception {
		log.debug(">>>>>user:{} ,logout", request.getSession().getAttribute("username"));

		String oldPassword = jsb.getString("oldPassword");
		String newPassword = jsb.getString("newPassword");

		User user = userMapper.selectByPrimaryKey(request.getSession().getAttribute("userId").toString());
		if (!user.getUserPassword().equals(oldPassword)) {
			throw new RebotException(RebotExceptionConstants.USER_VALID_PASSWORD_FAILD_CODE,
					RebotExceptionConstants.USER_VALID_PASSWORD_FAILD_MESSAGE,
					RebotExceptionConstants.USER_VALID_PASSWORD_FAILD_HTTP_STATUS);
		}

		user.setUserPassword(newPassword);
		user.setUptUser(request.getSession().getAttribute("username").toString());
		user.setUptTime(new Date());
		userMapper.updateByPrimaryKeySelective(user);

		Log log = new Log();
		log.setId(UUID.randomUUID().toString());
		log.setUserId(request.getSession().getAttribute("userId").toString());
		log.setUserName(request.getSession().getAttribute("username").toString());
		log.setMessage("用户" + request.getSession().getAttribute("username").toString() + "修改密码");
		log.setCrtTime(new Date());
		logMapper.insert(log);

		request.getSession().invalidate();
		ResponseSuccessResult responseResult = new ResponseSuccessResult(HttpStatus.OK.value(), "success");
		return new ResponseEntity<>(responseResult, HttpStatus.OK);
	}
}
