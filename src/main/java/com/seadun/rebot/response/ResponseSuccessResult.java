package com.seadun.rebot.response;

public class ResponseSuccessResult {
	private int code;
	private String description;
	private Object content;

	public ResponseSuccessResult(int code, String description, Object content) {
		this.code = code;
		this.description = description;
		this.content = content;
	}
	
	public ResponseSuccessResult(int code, String description) {
		this.code = code;
		this.description = description;
		this.content = "";
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
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

}
