package com.seadun.rebot.rest;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.seadun.rebot.entity.ContractDetail;
import com.seadun.rebot.mapper.ContractDetailMapper;
import com.seadun.rebot.response.ResponseSuccessResult;
import com.seadun.rebot.util.Utils;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/api/contract-detail")
@Slf4j
public class ContractDetailController {

	@Autowired
	private ContractDetailMapper contractDetailMapper;

	@PostMapping("count")
	public ResponseEntity<ResponseSuccessResult> count(@RequestBody ContractDetail contractDetail) {
		String requestid = UUID.randomUUID().toString();
		log.debug(">>>>>获取合同详细个数,requestid:{},param:{}", requestid, JSON.toJSONString(contractDetail));
		long total = contractDetailMapper.selectCount(contractDetail);

		JSONObject jsb = new JSONObject();
		jsb.put("total", total);

		log.debug(">>>>>获取合同详细个数,requestid:{},result:{}", requestid, jsb);
		ResponseSuccessResult responseResult = new ResponseSuccessResult(HttpStatus.OK.value(), "success", jsb);
		return new ResponseEntity<>(responseResult, HttpStatus.OK);
	}

	@PostMapping("count/{before}")
	public ResponseEntity<ResponseSuccessResult> count(@RequestBody ContractDetail contractDetail,
			@PathVariable int before) {
		String requestid = UUID.randomUUID().toString();
		log.debug(">>>>>获取过去几天合同详细个数,requestid:{},param:{},before:{}", requestid,JSON.toJSONString(contractDetail),before);
		long total = contractDetailMapper.selectCountBefore(contractDetail, Utils.getDateBefore(before));

		JSONObject jsb = new JSONObject();
		jsb.put("total", total);

		log.debug(">>>>>获取过去几天合同详细个数,requestid:{},result:{}", requestid,jsb);
		ResponseSuccessResult responseResult = new ResponseSuccessResult(HttpStatus.OK.value(), "success", jsb);
		return new ResponseEntity<>(responseResult, HttpStatus.OK);
	}
	
	@PostMapping("change")
	public ResponseEntity<ResponseSuccessResult> change(@RequestBody ContractDetail contractDetail) {
		log.debug(">>>>>更新详细,contractDetail:{}",JSON.toJSONString(contractDetail));
		contractDetailMapper.updateByPrimaryKeySelective(contractDetail);
		
		ResponseSuccessResult responseResult = new ResponseSuccessResult(HttpStatus.OK.value(), "success");
		return new ResponseEntity<>(responseResult, HttpStatus.OK);
	}
	
	
}
