package com.seadun.rebot.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.github.pagehelper.PageRowBounds;
import com.seadun.rebot.entity.Log;
import com.seadun.rebot.mapper.LogMapper;
import com.seadun.rebot.response.ResponseSuccessResult;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/log")
@Slf4j
public class LogController {
	@Autowired
	private LogMapper logMapper;
	
	
	// 角色分页
	@GetMapping("page")
	public ResponseEntity<ResponseSuccessResult> page(@RequestParam(required = true) int pageNo,
			@RequestParam(required = true) int pageSize) {
		log.debug(">>>>>获取用日志页数据,pageNo:{},pageSize:{}", pageNo, pageSize);
		PageRowBounds rowBounds = new PageRowBounds(pageNo, pageSize);
		
		List<Log> logList = logMapper.selectPage(rowBounds);
		PageInfo<Log> pageInfo = new PageInfo<Log>(logList);// 封装分页信息，便于前端展示

		ResponseSuccessResult responseResult = new ResponseSuccessResult(HttpStatus.OK.value(), "success", pageInfo);
		return new ResponseEntity<>(responseResult, HttpStatus.OK);
	}
}
