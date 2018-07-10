package com.seadun.rebot.entity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

import com.alibaba.fastjson.JSON;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RebotException extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8202816726589143414L;

	private String code;
	private String description;
	private Object content;
	private HttpStatus httpStatus;

	public RebotException(String code, String description, Object content, HttpStatus httpStatus) {
		log.info(">>>>>ConanException");
		log.info(">>>>>code:{}",code);
		log.info(">>>>>description:{}",description);
		log.info(">>>>>content:{}",JSON.toJSONString(content));
		log.info(">>>>>httpStatus:{}",httpStatus.toString());
		
		this.code = code;
		this.description = description;
		this.content = content;
		this.httpStatus = httpStatus;
	}

	public RebotException(String code, String description, HttpStatus httpStatus) {
		log.info(">>>>>ConanException");
		log.info(">>>>>code:{}",code);
		log.info(">>>>>description:{}",description);
		log.info(">>>>>httpStatus:{}",httpStatus.toString());
		
		this.code = code;
		this.description = description;
		this.httpStatus = httpStatus;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Object getContent() {
		return content;
	}

	public void setContent(Object content) {
		this.content = content;
	}

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

	public void setHttpStatus(HttpStatus httpStatus) {
		this.httpStatus = httpStatus;
	}

}
