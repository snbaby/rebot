package com.seadun.rebot.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.pagehelper.PageInfo;
import com.github.pagehelper.PageRowBounds;
import com.seadun.rebot.entity.Contract;
import com.seadun.rebot.mapper.ContractMapper;
import com.seadun.rebot.response.ResponseSuccessResult;
import com.seadun.rebot.service.ContractService;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/api/contract")
@Slf4j
public class ContractController {

	@Autowired
	private ContractMapper contractMapper;
	@Autowired
	private ContractService contractService;

	// 获取合同分页
	@GetMapping("page")
	public ResponseEntity<ResponseSuccessResult> page(@RequestParam(required = true) int pageNo,
			@RequestParam(required = true) int pageSize, String contract, String startTime,
			String endTime) {
		log.debug(">>>>>获取合同分页数据,pageNo:{},pageSize:{},contract:{},startTime:{},endTime{}", pageNo, pageSize, contract,
				startTime, endTime);
		PageRowBounds rowBounds = new PageRowBounds((pageNo - 1) * pageSize, pageSize);
		List<Contract> contractList = contractMapper.selectPage(rowBounds, contract, startTime, endTime);
		PageInfo<Contract> pageInfo = new PageInfo<Contract>(contractList);// 封装分页信息，便于前端展示

		ResponseSuccessResult responseResult = new ResponseSuccessResult(HttpStatus.OK.value(), "success", pageInfo);
		return new ResponseEntity<>(responseResult, HttpStatus.OK);
	}
	
	// 启用禁用某个合同
	@PutMapping("/{id}/{status}")
	public ResponseEntity<ResponseSuccessResult> statusChange(@PathVariable String id, @PathVariable String status) {
		log.debug(">>>>>合同狀態改變,id:{},status:{}",id,status);
		
		contractService.statusChange(id, status);

		ResponseSuccessResult responseResult = new ResponseSuccessResult(HttpStatus.OK.value(), "success");
		return new ResponseEntity<>(responseResult, HttpStatus.OK);
	}

}
