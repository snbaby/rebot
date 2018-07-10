package com.seadun.rebot.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.seadun.rebot.constant.RebotExceptionConstants;
import com.seadun.rebot.entity.RebotException;
import com.seadun.rebot.response.ResponseFaildResult;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class RebotControllerAdvice {
	/**
     * 全局异常捕捉处理
     * @param ex
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ResponseFaildResult> errorHandler(Exception ex) {
    	ex.printStackTrace();
    	log.error(">>>>>{}",JSON.toJSONString(ex));
    	ResponseFaildResult responseFaildResult = new ResponseFaildResult();
    	responseFaildResult.setCode(RebotExceptionConstants.INTERNAL_SERVER_ERROR_CODE);
    	responseFaildResult.setDescription(RebotExceptionConstants.INTERNAL_SERVER_ERROR_MESSAGE);
    	responseFaildResult.setContent(ex.getMessage());
		return new ResponseEntity<>(responseFaildResult,RebotExceptionConstants.INTERNAL_SERVER_ERROR_HTTP_STATUS);
    }
    
    /**
     * 自定义异常捕捉处理
     * @param ex
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = RebotException.class)
    public ResponseEntity<ResponseFaildResult> errorHandler(RebotException ex) {
    	log.error(">>>>>{}",JSON.toJSONString(ex));
    	ResponseFaildResult responseFaildResult = new ResponseFaildResult();
    	responseFaildResult.setCode(ex.getCode());
    	responseFaildResult.setDescription(ex.getDescription());
		responseFaildResult.setContent(ex.getContent());	
		return new ResponseEntity<>(responseFaildResult,ex.getHttpStatus());
    }
    
}
