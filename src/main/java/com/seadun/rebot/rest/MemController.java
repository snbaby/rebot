package com.seadun.rebot.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.seadun.rebot.entity.Memory;
import com.seadun.rebot.mapper.MemoryMapper;
import com.seadun.rebot.response.ResponseSuccessResult;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/mem")
@Slf4j
public class MemController {
	@Autowired
	private MemoryMapper memoryMapper;
	
	@GetMapping("list")
	public ResponseEntity<ResponseSuccessResult> list(String computerId) {
		log.debug(">>>>>内存数据,computerId:{}", computerId);
		Memory memory = new Memory();
		memory.setComputerId(computerId);
		List<Memory> memoryList = memoryMapper.select(memory);

		ResponseSuccessResult responseResult = new ResponseSuccessResult(HttpStatus.OK.value(), "success", memoryList);
		return new ResponseEntity<>(responseResult, HttpStatus.OK);
	}
}
