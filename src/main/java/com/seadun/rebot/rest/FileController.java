package com.seadun.rebot.rest;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.seadun.rebot.response.ResponseSuccessResult;
import com.seadun.rebot.service.FileService;

@RestController
@RequestMapping("api")
public class FileController {
	@Autowired
	private FileService fileService;
	
	//上傳excel文件
	@PostMapping("file")
	public ResponseEntity<ResponseSuccessResult> upload(@RequestParam(required = true) MultipartFile file) throws IOException {
		fileService.fileImport(file);
		ResponseSuccessResult responseResult = new ResponseSuccessResult(HttpStatus.OK.value(),"success");
		return new ResponseEntity<>(responseResult,HttpStatus.OK);
	}
	
	//下載excel文件
	@GetMapping("file")
	public ResponseEntity<ResponseSuccessResult> download() throws IOException {
		ResponseSuccessResult responseResult = new ResponseSuccessResult(HttpStatus.OK.value(),"success",
				"");
		return new ResponseEntity<>(responseResult,HttpStatus.OK);
	}

}
