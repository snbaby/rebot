package com.seadun.rebot.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONObject;
import com.seadun.rebot.mapper.ContractDetailMapper;
import com.seadun.rebot.response.ResponseSuccessResult;
import com.seadun.rebot.util.Utils;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("contract-detail")
@Slf4j
public class ContractDetailController {

	@Autowired
	private ContractDetailMapper contractDetailMapper;

	@GetMapping("count")
	public ResponseEntity<ResponseSuccessResult> count(String contractId) {
		log.debug(">>>>>获取合同详细个数,computerId:{}", contractId);
		long total = contractDetailMapper.selectCount(contractId);
		
		JSONObject jsb = new JSONObject();
		jsb.put("total", total);

		ResponseSuccessResult responseResult = new ResponseSuccessResult(HttpStatus.OK.value(), "success", jsb);
		return new ResponseEntity<>(responseResult, HttpStatus.OK);
	}
	
	@GetMapping("complete")
	public ResponseEntity<ResponseSuccessResult> complete(String contractId) {
		log.debug(">>>>>获取合同详细已完成个数,computerId:{}", contractId);
		long total = contractDetailMapper.selectComplete(contractId);
		
		JSONObject jsb = new JSONObject();
		jsb.put("total", total);

		ResponseSuccessResult responseResult = new ResponseSuccessResult(HttpStatus.OK.value(), "success", jsb);
		return new ResponseEntity<>(responseResult, HttpStatus.OK);
	}
	
	@GetMapping("un-complete")
	public ResponseEntity<ResponseSuccessResult> unComplete(String contractId) {
		log.debug(">>>>>获取合同详细未完成个数,computerId:{}", contractId);
		long total = contractDetailMapper.selectUnComplete(contractId);
		
		JSONObject jsb = new JSONObject();
		jsb.put("total", total);

		ResponseSuccessResult responseResult = new ResponseSuccessResult(HttpStatus.OK.value(), "success", jsb);
		return new ResponseEntity<>(responseResult, HttpStatus.OK);
	}
	
	@GetMapping("complete/before")
	public ResponseEntity<ResponseSuccessResult> completeBefore(String contractId,int before) {
		log.debug(">>>>>获取合同详细已完成个数,computerId:{}", contractId);
		long total = contractDetailMapper.selectCompleteBefore(contractId,Utils.getDateBefore(before));
		
		JSONObject jsb = new JSONObject();
		jsb.put("total", total);

		ResponseSuccessResult responseResult = new ResponseSuccessResult(HttpStatus.OK.value(), "success", jsb);
		return new ResponseEntity<>(responseResult, HttpStatus.OK);
	}

}
