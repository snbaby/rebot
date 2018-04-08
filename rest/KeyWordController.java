package com.conan.crawler.server.pre.rest;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.conan.crawler.server.pre.entity.KeyWordTb;
import com.conan.crawler.server.pre.entity.ResponseResult;
import com.conan.crawler.server.pre.mapper.KeyWordTbMapper;
import com.conan.crawler.server.pre.util.Utils;

@RestController
@RequestMapping("key-word")
public class KeyWordController {

	private int queryPageNumber = 1;

	@Autowired
	private KeyWordTbMapper keyWordTbMapper;
	@Autowired
	private KafkaTemplate kafkaTemplate;

	@RequestMapping(value = "upload", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<ResponseResult> postKeyWordUpload(
			@RequestParam(value = "keyWordFile", required = true) MultipartFile keyWordFile) throws Exception {
		InputStreamReader read = new InputStreamReader(keyWordFile.getInputStream(), "utf-8");
		BufferedReader reader = new BufferedReader(read);
		String line;
		while ((line = reader.readLine()) != null) {
			if (line.trim().length() > 0) {
				KeyWordTb record = new KeyWordTb();
				record.setId(UUID.randomUUID().toString());
				record.setKeyWord(line.trim());
				record.setCrtUser("admin");
				record.setCrtTime(new Date());
				record.setStatus("0");
				record.setCrtIp("127.0.0.1");
				keyWordTbMapper.insert(record);
			}
		}
		read.close();
		return new ResponseEntity<ResponseResult>(new ResponseResult(HttpStatus.CREATED.toString()),
				HttpStatus.CREATED);
	}

	@RequestMapping(value = "scan-start", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<ResponseResult> postKeyWordScanStart() throws Exception {
		List<KeyWordTb> keyWordTbList = keyWordTbMapper.selectByStatus("0");
		for (KeyWordTb keyWordTb : keyWordTbList) {
			for (int index = 0; index < queryPageNumber; index++) {
				ListenableFuture future = kafkaTemplate.send("key-word-scan", Utils.getKeyWordUrl(keyWordTb.getKeyWord(), index*44));
				future.addCallback(o -> System.out.println("key-word-scan-消息发送成功：" + keyWordTb.getKeyWord()),
						throwable -> System.out.println("key-word-scan消息发送失败：" + keyWordTb.getKeyWord()));
			}
		}

		return new ResponseEntity<ResponseResult>(
				new ResponseResult(HttpStatus.CREATED.toString(), keyWordTbMapper.selectByStatus("0")),
				HttpStatus.CREATED);
	}

}
