package com.seadun.rebot.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.pagehelper.PageInfo;
import com.github.pagehelper.PageRowBounds;
import com.seadun.rebot.constant.RebotExceptionConstants;
import com.seadun.rebot.entity.DetectionAccount;
import com.seadun.rebot.entity.RebotException;
import com.seadun.rebot.mapper.DetectionAccountMapper;
import com.seadun.rebot.response.ResponseSuccessResult;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("contract")
@Slf4j
public class ContractController {

	@Autowired
	private DetectionAccountMapper detectionAccountMapper;
	
	// 上傳excel文件
	@GetMapping("page")
	public ResponseEntity<ResponseSuccessResult> page(@RequestParam(required = true) int pageNo,
			@RequestParam(required = true) int pageSize, @RequestParam String contract, @RequestParam String startTime,
			@RequestParam String endTime) {
		log.debug(">>>>>获取合同分页数据,pageNo:{},pageSize:{},contract:{},startTime:{},endTime{}", pageNo, pageSize, contract,
				startTime, endTime);
		PageRowBounds page = new PageRowBounds(1, 10);
		List<DetectionAccount> DetectionAccountList = detectionAccountMapper.selectAll(page);
		 PageInfo<DetectionAccount> pageInfo = new PageInfo<DetectionAccount>(DetectionAccountList);//封装分页信息，便于前端展示
		
		ResponseSuccessResult responseResult = new ResponseSuccessResult(HttpStatus.OK.value(), "success",pageInfo);
		return new ResponseEntity<>(responseResult, HttpStatus.OK);
	}

}
