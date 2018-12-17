package com.seadun.rebot.rest;

import java.io.IOException;
import java.net.URLEncoder;
import java.text.ParseException;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.seadun.rebot.entity.Contract;
import com.seadun.rebot.mapper.ContractMapper;
import com.seadun.rebot.response.ResponseSuccessResult;
import com.seadun.rebot.service.FileService;
import com.seadun.rebot.util.Utils;

@RestController
@RequestMapping("api")
public class FileController {
	@Autowired
	private FileService fileService;
	
	@Autowired
	private ContractMapper contractMapper;
	
	//上傳excel文件
	@PostMapping("file")
	public ResponseEntity<ResponseSuccessResult> upload(@RequestParam(required = true) MultipartFile file) throws IOException {
		fileService.fileImport(file);
		ResponseSuccessResult responseResult = new ResponseSuccessResult(HttpStatus.OK.value(),"success");
		return new ResponseEntity<>(responseResult,HttpStatus.OK);
	}
	
	//下載excel文件
	@GetMapping("file")
	public void download(HttpServletResponse response,String contractDetailStatus,String contractId,String startTime,String endTime
			) throws IOException, ParseException {
		Contract contract = contractMapper.selectByPrimaryKey(contractId);
		// 告诉浏览器用什么软件可以打开此文件
        response.setHeader("content-Type", "application/vnd.ms-excel");
        
        String fileName = "export";
        if(contract!=null && StringUtils.isNotBlank(contract.getContract())) {
        	fileName = contract.getContract();
        }
        // 下载文件的默认名称
        response.setHeader("Content-Disposition", "attachment;filename="+URLEncoder.encode(fileName+".xls", "utf-8"));
		fileService.fileExport(response, contractDetailStatus, contractId, startTime, endTime);
	}
	
	//下載excel文件
	@GetMapping("file/contract")
	public void downloadContract(HttpServletResponse response,String contractId) throws IOException, ParseException {
		Contract contract = contractMapper.selectByPrimaryKey(contractId);
		// 告诉浏览器用什么软件可以打开此文件
        response.setHeader("content-Type", "application/vnd.ms-excel");
        
        String fileName = "验机报告";
        if(contract!=null && StringUtils.isNotBlank(contract.getContract())) {
        	fileName = contract.getContract() + "-" +fileName;
        }
        // 下载文件的默认名称
        response.setHeader("Content-Disposition", "attachment;filename="+URLEncoder.encode(fileName+".xls", "utf-8"));
		fileService.contractExport(response, contractId);
	}
	
	//下載excel文件
	@PostMapping("compress")
	public ResponseEntity<ResponseSuccessResult>  compress(HttpServletResponse response,@RequestBody JSONObject jsonObject) throws Exception {
		ResponseSuccessResult responseResult = new ResponseSuccessResult(HttpStatus.OK.value(),Utils.compress(jsonObject.toJSONString()));
		return new ResponseEntity<>(responseResult,HttpStatus.OK);
	}
}
