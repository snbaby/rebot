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
import com.seadun.rebot.entity.ContractComputer;
import com.seadun.rebot.mapper.ContractComputerMapper;
import com.seadun.rebot.response.ResponseSuccessResult;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/inspection")
@Slf4j
public class InspectionController {
	
	@Autowired
	private ContractComputerMapper contractComputerMapper;
	// 获取驗機分页
	@GetMapping("page")
	public ResponseEntity<ResponseSuccessResult> page(@RequestParam(required = true) int pageNo,
			@RequestParam(required = true) int pageSize,String contractId,String computerId,String contractStatus,String contractDetailStatus) {
		log.debug(">>>>>获取验机分页数据,pageNo:{},pageSize:{},contract:{},startTime:{},endTime{}", pageNo, pageSize);
		PageRowBounds rowBounds = new PageRowBounds((pageNo - 1) * pageSize, pageSize);
		List<ContractComputer> contractComputerList = contractComputerMapper.selectPage(rowBounds,contractId,computerId, contractStatus, contractDetailStatus);
		PageInfo<ContractComputer> pageInfo = new PageInfo<ContractComputer>(contractComputerList);// 封装分页信息，便于前端展示

		ResponseSuccessResult responseResult = new ResponseSuccessResult(HttpStatus.OK.value(), "success", pageInfo);
		return new ResponseEntity<>(responseResult, HttpStatus.OK);
	}
}
