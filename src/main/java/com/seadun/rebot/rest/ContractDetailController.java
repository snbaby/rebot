package com.seadun.rebot.rest;

import java.util.Date;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.seadun.rebot.entity.ContractDetail;
import com.seadun.rebot.mapper.ComputerMapper;
import com.seadun.rebot.mapper.ContractDetailMapper;
import com.seadun.rebot.mapper.DiskMapper;
import com.seadun.rebot.mapper.MemoryMapper;
import com.seadun.rebot.mapper.NetworkMapper;
import com.seadun.rebot.mapper.VideoMapper;
import com.seadun.rebot.response.ResponseSuccessResult;
import com.seadun.rebot.util.Utils;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/api/contract-detail")
@Slf4j
public class ContractDetailController {

	@Autowired
	private ContractDetailMapper contractDetailMapper;
	@Autowired
	private ComputerMapper computerMapper;
	@Autowired
	private DiskMapper diskMapper;
	@Autowired
	private MemoryMapper memoryMapper;
	@Autowired
	private NetworkMapper networkMapper;
	@Autowired
	private VideoMapper videoMapper;

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
	
	@GetMapping("count/time")
	public ResponseEntity<ResponseSuccessResult> count(String startTime,String endTime,String status) {
		log.debug(">>>>>获取合同详细个数,startTime:{},endTime:{},status:{}", startTime, endTime, status);
		long total = contractDetailMapper.selectCountTime(startTime, endTime, status);

		JSONObject jsb = new JSONObject();
		jsb.put("total", total);

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
		contractDetail.setUptTime(new Date());
		contractDetailMapper.updateByPrimaryKeySelective(contractDetail);
		
		ResponseSuccessResult responseResult = new ResponseSuccessResult(HttpStatus.OK.value(), "success");
		return new ResponseEntity<>(responseResult, HttpStatus.OK);
	}
	
	// 刪除合同對應的所有資源
		@DeleteMapping("/{id}")
		public ResponseEntity<ResponseSuccessResult> delete(@PathVariable String id) {
			log.debug(">>>>>刪除合同某個資產號,id:{}",id);
			
			ContractDetail contractDetail = contractDetailMapper.selectByPrimaryKey(id);
			String computerId = contractDetail.getComputerId();
			if(StringUtils.isNoneBlank(computerId)) {
				computerMapper.deleteByPrimaryKey(computerId);
				diskMapper.deleteByComputerId(computerId);
				memoryMapper.deleteByComputerId(computerId);
				networkMapper.deleteByComputerId(computerId);
				videoMapper.deleteByComputerId(computerId);
			}
			
			contractDetailMapper.deleteByPrimaryKey(id);

			ResponseSuccessResult responseResult = new ResponseSuccessResult(HttpStatus.OK.value(), "success");
			return new ResponseEntity<>(responseResult, HttpStatus.OK);
		}
}
