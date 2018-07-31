package com.seadun.rebot.rest;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.PageRowBounds;
import com.seadun.rebot.constant.RebotConstants;
import com.seadun.rebot.entity.Contract;
import com.seadun.rebot.entity.ContractComputer;
import com.seadun.rebot.entity.ContractDetail;
import com.seadun.rebot.mapper.ContractComputerMapper;
import com.seadun.rebot.mapper.ContractDetailMapper;
import com.seadun.rebot.mapper.ContractMapper;
import com.seadun.rebot.response.ResponseSuccessResult;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/inspection")
@Slf4j
public class InspectionController {
	
	@Autowired
	private ContractComputerMapper contractComputerMapper;
	@Autowired
	private ContractMapper contractMapper;
	@Autowired
	private ContractDetailMapper contractDetailMapper;
	// 获取驗機分页
	@GetMapping("page")
	public ResponseEntity<ResponseSuccessResult> page(@RequestParam(required = true) int pageNo,
			@RequestParam(required = true) int pageSize,String contractId,String computerId,String contractStatus,String contractDetailStatus,String startTime,String endTime) {
		log.debug(">>>>>获取验机分页数据,pageNo:{},pageSize:{},contract:{},startTime:{},endTime{}", pageNo, pageSize);
		PageRowBounds rowBounds = new PageRowBounds((pageNo - 1) * pageSize, pageSize);
		List<ContractComputer> contractComputerList = contractComputerMapper.selectPage(rowBounds,contractId,computerId, contractStatus, contractDetailStatus,startTime,endTime);
		PageInfo<ContractComputer> pageInfo = new PageInfo<ContractComputer>(contractComputerList);// 封装分页信息，便于前端展示

		ResponseSuccessResult responseResult = new ResponseSuccessResult(HttpStatus.OK.value(), "success", pageInfo);
		return new ResponseEntity<>(responseResult, HttpStatus.OK);
	}
	
	// 合同入库
	@PostMapping("import")
	public ResponseEntity<ResponseSuccessResult> contractImport(@RequestBody JSONObject jsonObj) {
		log.debug(">>>>>手动导入合同,jsonObj:{}", jsonObj);
		String eqType = jsonObj.getString("eqType");
		List<String> eqNos = jsonObj.getJSONArray("eqNos").toJavaList(String.class);
		
		// 新增合同，如果合同存在则更新更新时间
		String uuid = UUID.randomUUID().toString();

		Contract contract = new Contract();
		contract.setId(uuid);
		contract.setContract(jsonObj.getString("contract"));
		contract.setStatus(RebotConstants.CONTRACT_UNVERIFIED);
		contract.setUptTime(new Date());
		contract.setCrtTime(new Date());

		contractMapper.insertSelective(contract);
		
		eqNos.forEach(eqNo -> {
			// 新增资产表，入存在，则更新状态
			ContractDetail contractDetail = new ContractDetail();
			contractDetail.setId(UUID.randomUUID().toString());
			contractDetail.setContractId(uuid);
			contractDetail.setEqNo(eqNo);
			contractDetail.setEqType(eqType);
			contractDetail.setStatus(RebotConstants.CONTRACT_UNCONFIRM);
			contractDetail.setComputerId("");
			contractDetail.setCrtTime(new Date());
			contractDetail.setUptTime(new Date());
			contractDetailMapper.insertSelective(contractDetail);
		});

		ResponseSuccessResult responseResult = new ResponseSuccessResult(HttpStatus.OK.value(), "success");
		return new ResponseEntity<>(responseResult, HttpStatus.OK);
	}
}
