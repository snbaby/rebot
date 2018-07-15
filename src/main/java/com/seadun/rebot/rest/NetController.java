package com.seadun.rebot.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.seadun.rebot.entity.Network;
import com.seadun.rebot.mapper.NetworkMapper;
import com.seadun.rebot.response.ResponseSuccessResult;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/net")
@Slf4j
public class NetController {
	
	@Autowired
	private NetworkMapper networkMapper;
	
	@GetMapping("list")
	public ResponseEntity<ResponseSuccessResult> list(String computerId) {
		log.debug(">>>>>MAC数据,computerId:{}", computerId);
		Network network = new Network();
		network.setComputerId(computerId);
		List<Network> networkList = networkMapper.select(network);

		ResponseSuccessResult responseResult = new ResponseSuccessResult(HttpStatus.OK.value(), "success", networkList);
		return new ResponseEntity<>(responseResult, HttpStatus.OK);
	}
}
