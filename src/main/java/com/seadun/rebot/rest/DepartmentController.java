package com.seadun.rebot.rest;

import java.util.Date;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
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
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.seadun.rebot.entity.Department;
import com.seadun.rebot.mapper.DepartmentMapper;
import com.seadun.rebot.response.ResponseSuccessResult;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/department")
@Slf4j
public class DepartmentController {
	@Autowired
	private DepartmentMapper departmentMapper;
	
	@PostMapping("add")
	public ResponseEntity<ResponseSuccessResult> add(HttpServletRequest request,
			@RequestBody Department departmentParam) {
		log.debug(">>>>>新增部门,Department:{}", JSON.toJSONString(departmentParam));
		String loginUser = request.getSession().getAttribute("username").toString();// 登录成功
		if(StringUtils.isBlank(departmentParam.getParentId())) {//新增根節點部門
			Department department = new Department();
			department.setId(UUID.randomUUID().toString());
			department.setName(departmentParam.getName());
			department.setParentId("-1");
			department.setDepPath("");
			department.setCrtUser(loginUser);
			department.setCrtDate(new Date());
			departmentMapper.insertSelective(department);
		}else {//新增子部門
			Department departmentTemp = departmentMapper.selectByPrimaryKey(departmentParam.getParentId());
			
			Department department = new Department();
			department.setId(UUID.randomUUID().toString());
			department.setName(departmentParam.getName());
			department.setParentId(departmentTemp.getId());
			department.setDepPath(departmentTemp.getParentId()+"/"+departmentTemp.getId());
			department.setCrtUser(loginUser);
			department.setCrtDate(new Date());
			departmentMapper.insertSelective(department);
		}

		ResponseSuccessResult responseResult = new ResponseSuccessResult(HttpStatus.OK.value(), "success");
		return new ResponseEntity<>(responseResult, HttpStatus.OK);
	}
	
	@GetMapping("tree")
	public ResponseEntity<ResponseSuccessResult> tree() {
		log.debug(">>>>>獲取部门樹");
		ResponseSuccessResult responseResult = new ResponseSuccessResult(HttpStatus.OK.value(), "success", departmentMapper.selectTree());
		return new ResponseEntity<>(responseResult, HttpStatus.OK);
	}
	
	@GetMapping("list")
	public ResponseEntity<ResponseSuccessResult> list() {
		log.debug(">>>>>獲取部门列表");
		ResponseSuccessResult responseResult = new ResponseSuccessResult(HttpStatus.OK.value(), "success", departmentMapper.list());
		return new ResponseEntity<>(responseResult, HttpStatus.OK);
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity<ResponseSuccessResult> deleteDepartment(@PathVariable(value = "id") String id) {
		log.debug(">>>>>删除部门樹,id:{}",id);
		departmentMapper.deleteByid(id);
		ResponseSuccessResult responseResult = new ResponseSuccessResult(HttpStatus.OK.value(), "success");
		return new ResponseEntity<>(responseResult, HttpStatus.OK);
	}
	
	@PutMapping("edit")
	public ResponseEntity<ResponseSuccessResult> edit(HttpServletRequest request,
			@RequestBody Department departmentParam) {
		
		log.debug(">>>>>修改部门名称,Department:{}", JSON.toJSONString(departmentParam));
		String loginUser = request.getSession().getAttribute("username").toString();// 登录成功
		Department department = departmentMapper.selectByPrimaryKey(departmentParam.getId());
		department.setName(departmentParam.getName());
		department.setUptTime(new Date());
		department.setUptUser(loginUser);
		departmentMapper.updateByPrimaryKeySelective(department);

		ResponseSuccessResult responseResult = new ResponseSuccessResult(HttpStatus.OK.value(), "success");
		return new ResponseEntity<>(responseResult, HttpStatus.OK);
	}
}
