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
import com.seadun.rebot.entity.Contract;
import com.seadun.rebot.entity.Department;
import com.seadun.rebot.entity.Role;
import com.seadun.rebot.mapper.RoleMapper;
import com.seadun.rebot.response.ResponseSuccessResult;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/role")
@Slf4j
public class RoleController {
	@Autowired
	private RoleMapper roleMapper;
	
	@PostMapping("add")
	public ResponseEntity<ResponseSuccessResult> add(HttpServletRequest request,
			@RequestBody Role roleParam) {
		log.debug(">>>>>新增橘色,roleParam:{}", JSON.toJSONString(roleParam));
		String loginUser = request.getSession().getAttribute("username").toString();// 登录成功
		
		Role role = new Role();
		role.setId(UUID.randomUUID().toString());
		role.setName(roleParam.getName());
		role.setCrtTime(new Date());
		role.setCrtUser(loginUser);
		
		roleMapper.insertSelective(role);

		ResponseSuccessResult responseResult = new ResponseSuccessResult(HttpStatus.OK.value(), "success");
		return new ResponseEntity<>(responseResult, HttpStatus.OK);
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity<ResponseSuccessResult> deleteDepartment(@PathVariable(value = "id") String id) {
		log.debug(">>>>>删除角色,id:{}",id);
		roleMapper.deleteByPrimaryKey(id);
		ResponseSuccessResult responseResult = new ResponseSuccessResult(HttpStatus.OK.value(), "success");
		return new ResponseEntity<>(responseResult, HttpStatus.OK);
	}
	
	@PutMapping("edit")
	public ResponseEntity<ResponseSuccessResult> edit(HttpServletRequest request,
			@RequestBody Role roleParam) {
		
		log.debug(">>>>>修改角色名称,RoleParam:{}", JSON.toJSONString(roleParam));
		String loginUser = request.getSession().getAttribute("username").toString();// 登录成功
		Role role = roleMapper.selectByPrimaryKey(roleParam.getId());
		role.setName(roleParam.getName());
		role.setUptUser(loginUser);
		role.setUptTime(new Date());
		roleMapper.updateByPrimaryKeySelective(role);

		ResponseSuccessResult responseResult = new ResponseSuccessResult(HttpStatus.OK.value(), "success");
		return new ResponseEntity<>(responseResult, HttpStatus.OK);
	}
	
	// 角色分页
	@GetMapping("page")
	public ResponseEntity<ResponseSuccessResult> page(@RequestParam(required = true) int pageNo,
			@RequestParam(required = true) int pageSize) {
		log.debug(">>>>>获取角色分页数据,pageNo:{},pageSize:{}", pageNo, pageSize);
		PageRowBounds rowBounds = new PageRowBounds(pageNo, pageSize);
		
		List<Role> roleList = roleMapper.selectPage(rowBounds);
		PageInfo<Role> pageInfo = new PageInfo<Role>(roleList);// 封装分页信息，便于前端展示

		ResponseSuccessResult responseResult = new ResponseSuccessResult(HttpStatus.OK.value(), "success", pageInfo);
		return new ResponseEntity<>(responseResult, HttpStatus.OK);
	}
	
	// 角色分页
	@GetMapping("list")
	public ResponseEntity<ResponseSuccessResult> list() {
		log.debug(">>>>>获取角色所有数据");
		
		List<Role> roleList = roleMapper.list();

		ResponseSuccessResult responseResult = new ResponseSuccessResult(HttpStatus.OK.value(), "success", roleList);
		return new ResponseEntity<>(responseResult, HttpStatus.OK);
	}
}
