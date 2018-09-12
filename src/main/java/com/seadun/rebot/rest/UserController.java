package com.seadun.rebot.rest;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.PageRowBounds;
import com.seadun.rebot.entity.User;
import com.seadun.rebot.mapper.UserMapper;
import com.seadun.rebot.response.ResponseSuccessResult;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/user")
@Slf4j
public class UserController {
	@Autowired
	private UserMapper userMapper;
	
	@PostMapping("add")
	public ResponseEntity<ResponseSuccessResult> add(HttpServletRequest request,
			@RequestBody User userParam) {
		log.debug(">>>>>新增用户,userParam:{}", JSON.toJSONString(userParam));
		String loginUser = request.getSession().getAttribute("username").toString();// 登录成功
		
		User user = new User();
		user.setId(UUID.randomUUID().toString());
		user.setUserName(userParam.getUserName());
		user.setUserPassword(userParam.getUserPassword());
		user.setRoleId(userParam.getRoleId());
		user.setDepartmentId(userParam.getDepartmentId());
		user.setCrtTime(new Date());
		user.setCrtUser(loginUser);
		
		userMapper.insertSelective(user);

		ResponseSuccessResult responseResult = new ResponseSuccessResult(HttpStatus.OK.value(), "success");
		return new ResponseEntity<>(responseResult, HttpStatus.OK);
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity<ResponseSuccessResult> deleteDepartment(@PathVariable(value = "id") String id) {
		log.debug(">>>>>删除用户,id:{}",id);
		userMapper.deleteByPrimaryKey(id);
		ResponseSuccessResult responseResult = new ResponseSuccessResult(HttpStatus.OK.value(), "success");
		return new ResponseEntity<>(responseResult, HttpStatus.OK);
	}
	
	@PutMapping("edit")
	public ResponseEntity<ResponseSuccessResult> edit(HttpServletRequest request,
			@RequestBody User userParam) {
		
		log.debug(">>>>>修改用户名称,userParam:{}", JSON.toJSONString(userParam));
		String loginUser = request.getSession().getAttribute("username").toString();// 登录成功
		User user = userMapper.selectByPrimaryKey(userParam.getId());
		user.setUserName(userParam.getUserName());
		user.setUserPassword(userParam.getUserPassword());
		user.setRoleId(userParam.getRoleId());
		user.setDepartmentId(userParam.getDepartmentId());
		user.setUptUser(loginUser);
		user.setUptTime(new Date());
		userMapper.updateByPrimaryKeySelective(user);

		ResponseSuccessResult responseResult = new ResponseSuccessResult(HttpStatus.OK.value(), "success");
		return new ResponseEntity<>(responseResult, HttpStatus.OK);
	}
	
	// 角色分页
	@GetMapping("page")
	public ResponseEntity<ResponseSuccessResult> page(@RequestParam(required = true) int pageNo,
			@RequestParam(required = true) int pageSize) {
		log.debug(">>>>>获取用户分页数据,pageNo:{},pageSize:{}", pageNo, pageSize);
		PageRowBounds rowBounds = new PageRowBounds(pageNo, pageSize);
		
		List<User> userList = userMapper.selectPage(rowBounds);
		PageInfo<User> pageInfo = new PageInfo<User>(userList);// 封装分页信息，便于前端展示

		ResponseSuccessResult responseResult = new ResponseSuccessResult(HttpStatus.OK.value(), "success", pageInfo);
		return new ResponseEntity<>(responseResult, HttpStatus.OK);
	}
}
