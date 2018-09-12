package com.seadun.rebot.rest;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.pagehelper.PageInfo;
import com.github.pagehelper.PageRowBounds;
import com.seadun.rebot.entity.Contract;
import com.seadun.rebot.entity.Log;
import com.seadun.rebot.mapper.ComputerMapper;
import com.seadun.rebot.mapper.ContractDetailMapper;
import com.seadun.rebot.mapper.ContractMapper;
import com.seadun.rebot.mapper.DiskMapper;
import com.seadun.rebot.mapper.LogMapper;
import com.seadun.rebot.mapper.MemoryMapper;
import com.seadun.rebot.mapper.NetworkMapper;
import com.seadun.rebot.mapper.VideoMapper;
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
	@Autowired
	private LogMapper logMapper;

	// 获取合同分页
	@GetMapping("page")
	public ResponseEntity<ResponseSuccessResult> page(@RequestParam(required = true) int pageNo,
			@RequestParam(required = true) int pageSize, String contract, String startTime,
			String endTime) {
		log.debug(">>>>>获取合同分页数据,pageNo:{},pageSize:{},contract:{},startTime:{},endTime{}", pageNo, pageSize, contract,
				startTime, endTime);
		
		PageRowBounds rowBounds = new PageRowBounds(pageNo, pageSize);
		List<Contract> contractList = contractMapper.selectPage(rowBounds, contract, startTime, endTime);
		PageInfo<Contract> pageInfo = new PageInfo<Contract>(contractList);// 封装分页信息，便于前端展示

		ResponseSuccessResult responseResult = new ResponseSuccessResult(HttpStatus.OK.value(), "success", pageInfo);
		return new ResponseEntity<>(responseResult, HttpStatus.OK);
	}
	
	// 获取合同
	@GetMapping("list")
	public ResponseEntity<ResponseSuccessResult> list() {
		log.debug(">>>>>获取所有合同数据");
		
		List<Contract> contractList = contractMapper.select(new Contract());

		ResponseSuccessResult responseResult = new ResponseSuccessResult(HttpStatus.OK.value(), "success", contractList);
		return new ResponseEntity<>(responseResult, HttpStatus.OK);
	}
	
	// 时间段获取合同
	@GetMapping("list/time")
	public ResponseEntity<ResponseSuccessResult> listFilterTime(String startTime) {
		log.debug(">>>>>获取所有合同数据");
		
		List<Contract> contractList = contractMapper.selectFilterTime(startTime);

		ResponseSuccessResult responseResult = new ResponseSuccessResult(HttpStatus.OK.value(), "success", contractList);
		return new ResponseEntity<>(responseResult, HttpStatus.OK);
	}
	
	// 启用禁用某个合同
	@PutMapping("/{id}/{status}")
	public ResponseEntity<ResponseSuccessResult> statusChange(HttpServletRequest request,@PathVariable String id, @PathVariable String status) {
		log.debug(">>>>>合同狀態改變,id:{},status:{}",id,status);
		
		Log log = new Log();
		log.setId(UUID.randomUUID().toString());
		log.setUserId(request.getSession().getAttribute("userId").toString());
		log.setUserName(request.getSession().getAttribute("username").toString());
		log.setMessage("用户"+request.getSession().getAttribute("username").toString()+"设置合同状态:"+status);
		log.setCrtTime(new Date());
		logMapper.insert(log);
		
		contractService.statusChange(id, status);

		ResponseSuccessResult responseResult = new ResponseSuccessResult(HttpStatus.OK.value(), "success");
		return new ResponseEntity<>(responseResult, HttpStatus.OK);
	}
	
	// 刪除合同對應的所有資源
	@DeleteMapping("/{id}")
	public ResponseEntity<ResponseSuccessResult> delete(HttpServletRequest request,@PathVariable String id) {
		log.debug(">>>>>刪除合同對應的所有資源,id:{}",id);
		
		Contract contract = contractMapper.selectByPrimaryKey(id);
		
		Log log = new Log();
		log.setId(UUID.randomUUID().toString());
		log.setUserId(request.getSession().getAttribute("userId").toString());
		log.setUserName(request.getSession().getAttribute("username").toString());
		log.setMessage("用户"+request.getSession().getAttribute("username").toString()+"删除合同:"+contract.getContract());
		log.setCrtTime(new Date());
		logMapper.insert(log);
		
		contractMapper.deleteByPrimaryKey(id);
		
		List<String> computerIds = contractDetailMapper.selectComputerIds(id);
		if(computerIds.size()>0) {
			computerMapper.deleteByPrimaryKeys(computerIds);
			diskMapper.deleteByComputerIds(computerIds);
			memoryMapper.deleteByComputerIds(computerIds);
			networkMapper.deleteByComputerIds(computerIds);
			videoMapper.deleteByComputerIds(computerIds);
		}
		
		contractDetailMapper.deleteByContractId(id);

		ResponseSuccessResult responseResult = new ResponseSuccessResult(HttpStatus.OK.value(), "success");
		return new ResponseEntity<>(responseResult, HttpStatus.OK);
	}

}
