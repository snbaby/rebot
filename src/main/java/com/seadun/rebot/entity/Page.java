package com.seadun.rebot.entity;

import lombok.Data;

@Data
public class Page {
	private int pageNo;
	private int pageSize;
	private long total;
}
