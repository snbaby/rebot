package com.seadun.rebot.constant;

import org.springframework.http.HttpStatus;

public class RebotExceptionConstants {
	public final static String USER_VALID_FAILD_CODE =  "USER_VALID_FAILD";
	public final static String USER_VALID_FAILD_MESSAGE =  "用户名或密码错误.";
	
	// 系统内部错误
	public static final String INTERNAL_SERVER_ERROR_CODE = "INTERNAL_SERVER_ERROR";
	public static final String INTERNAL_SERVER_ERROR_MESSAGE = "系统内部异常";
	public static final HttpStatus INTERNAL_SERVER_ERROR_HTTP_STATUS = HttpStatus.INTERNAL_SERVER_ERROR;
	
	// excel解析错误
	public static final String EXCEL_PRASE_ERROR_CODE = "EXCEL_PRASE_ERROR";
	public static final String EXCEL_PRASE_ERROR_MESSAGE = "excel 格式错误";
	public static final HttpStatus EXCEL_PRASE_ERROR_HTTP_STATUS = HttpStatus.BAD_REQUEST;
	
	// excel解析错误
	public static final String EXCEL_CONTENT_ERROR_CODE = "EXCEL_CONTENT_ERROR";
	public static final String EXCEL_CONTENT_ERROR_MESSAGE = "excel內容错误";
	public static final HttpStatus EXCEL_CONTENT_ERROR_HTTP_STATUS = HttpStatus.BAD_REQUEST;
	
}
