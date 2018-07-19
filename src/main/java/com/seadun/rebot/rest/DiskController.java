package com.seadun.rebot.rest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.seadun.rebot.entity.Disk;
import com.seadun.rebot.mapper.DiskMapper;
import com.seadun.rebot.response.ResponseSuccessResult;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/disk")
@Slf4j
public class DiskController {
	
	@Autowired
	private DiskMapper diskMapper;
	
	@GetMapping("list")
	public ResponseEntity<ResponseSuccessResult> list(String computerId) {
		log.debug(">>>>>硬盘数据,computerId:{}", computerId);
		Disk disk = new Disk();
		disk.setComputerId(computerId);
		List<Disk> diskList = diskMapper.select(disk);

		ResponseSuccessResult responseResult = new ResponseSuccessResult(HttpStatus.OK.value(), "success", diskList);
		return new ResponseEntity<>(responseResult, HttpStatus.OK);
	}
	
	@PutMapping("update")
	public ResponseEntity<ResponseSuccessResult> update(@RequestBody JSONObject jsonParam) {
		List<Disk> diskList = new ArrayList<>();
		diskList =  jsonParam.getJSONArray("disks").toJavaList(Disk.class);
		diskList.forEach(disk->{
			disk.setUptTime(new Date());
			diskMapper.updateByPrimaryKeySelective(disk);
		});
		ResponseSuccessResult responseResult = new ResponseSuccessResult(HttpStatus.OK.value(), "success");
		return new ResponseEntity<>(responseResult, HttpStatus.OK);
	}
}
