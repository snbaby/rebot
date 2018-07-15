package com.seadun.rebot.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.seadun.rebot.entity.Video;
import com.seadun.rebot.mapper.VideoMapper;
import com.seadun.rebot.response.ResponseSuccessResult;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/video")
@Slf4j
public class VideoController {
	
	@Autowired
	private VideoMapper videoMapper;
	
	@GetMapping("list")
	public ResponseEntity<ResponseSuccessResult> list(String computerId) {
		log.debug(">>>>>显卡数据,computerId:{}", computerId);
		Video video = new Video();
		video.setComputerId(computerId);
		List<Video> videoList = videoMapper.select(video);

		ResponseSuccessResult responseResult = new ResponseSuccessResult(HttpStatus.OK.value(), "success", videoList);
		return new ResponseEntity<>(responseResult, HttpStatus.OK);
	}
}
