package com.seadun.rebot.rest;

import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.seadun.rebot.entity.DictMemoryType;
import com.seadun.rebot.entity.User;
import com.seadun.rebot.mapper.DictMemoryTypeMapper;
import com.seadun.rebot.mapper.UserMapper;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/api/init")
@Slf4j
public class InitController {
	@Autowired
	private DictMemoryTypeMapper dictMemoryTypeMapper;
	@Autowired
	private UserMapper userMapper;
	
	@GetMapping(value = { "/memory-type" })
	@ResponseBody
	public void memoryType() {
		log.info(">>>>>init memory-type");
		DictMemoryType dictMemoryType = new DictMemoryType();
		
		dictMemoryType.setId(UUID.randomUUID().toString());
		dictMemoryType.setMemoryTypeId("0");
		dictMemoryType.setMemoryTypeName("Unknown");
		dictMemoryTypeMapper.insert(dictMemoryType);
		
		dictMemoryType.setId(UUID.randomUUID().toString());
		dictMemoryType.setMemoryTypeId("1");
		dictMemoryType.setMemoryTypeName("Other");
		dictMemoryTypeMapper.insert(dictMemoryType);
		
		dictMemoryType.setId(UUID.randomUUID().toString());
		dictMemoryType.setMemoryTypeId("2");
		dictMemoryType.setMemoryTypeName("DRAM");
		dictMemoryTypeMapper.insert(dictMemoryType);
		
		dictMemoryType.setId(UUID.randomUUID().toString());
		dictMemoryType.setMemoryTypeId("3");
		dictMemoryType.setMemoryTypeName("Synchronous DRAM");
		dictMemoryTypeMapper.insert(dictMemoryType);
		
		dictMemoryType.setId(UUID.randomUUID().toString());
		dictMemoryType.setMemoryTypeId("4");
		dictMemoryType.setMemoryTypeName("Cache DRAM");
		dictMemoryTypeMapper.insert(dictMemoryType);
		
		dictMemoryType.setId(UUID.randomUUID().toString());
		dictMemoryType.setMemoryTypeId("5");
		dictMemoryType.setMemoryTypeName("EDO");
		dictMemoryTypeMapper.insert(dictMemoryType);
		
		dictMemoryType.setId(UUID.randomUUID().toString());
		dictMemoryType.setMemoryTypeId("6");
		dictMemoryType.setMemoryTypeName("EDRAM");
		dictMemoryTypeMapper.insert(dictMemoryType);
		
		
		dictMemoryType.setId(UUID.randomUUID().toString());
		dictMemoryType.setMemoryTypeId("7");
		dictMemoryType.setMemoryTypeName("VRAM");
		dictMemoryTypeMapper.insert(dictMemoryType);
		
		dictMemoryType.setId(UUID.randomUUID().toString());
		dictMemoryType.setMemoryTypeId("8");
		dictMemoryType.setMemoryTypeName("SRAM");
		dictMemoryTypeMapper.insert(dictMemoryType);
		
		dictMemoryType.setId(UUID.randomUUID().toString());
		dictMemoryType.setMemoryTypeId("9");
		dictMemoryType.setMemoryTypeName("RAM");
		dictMemoryTypeMapper.insert(dictMemoryType);
		
		dictMemoryType.setId(UUID.randomUUID().toString());
		dictMemoryType.setMemoryTypeId("10");
		dictMemoryType.setMemoryTypeName("ROM");
		dictMemoryTypeMapper.insert(dictMemoryType);
		
		dictMemoryType.setId(UUID.randomUUID().toString());
		dictMemoryType.setMemoryTypeId("11");
		dictMemoryType.setMemoryTypeName("Flash");
		dictMemoryTypeMapper.insert(dictMemoryType);
		
		dictMemoryType.setId(UUID.randomUUID().toString());
		dictMemoryType.setMemoryTypeId("12");
		dictMemoryType.setMemoryTypeName("EEPROM");
		dictMemoryTypeMapper.insert(dictMemoryType);
		
		dictMemoryType.setId(UUID.randomUUID().toString());
		dictMemoryType.setMemoryTypeId("13");
		dictMemoryType.setMemoryTypeName("FEPROM");
		dictMemoryTypeMapper.insert(dictMemoryType);
		
		dictMemoryType.setId(UUID.randomUUID().toString());
		dictMemoryType.setMemoryTypeId("14");
		dictMemoryType.setMemoryTypeName("EPROM");
		dictMemoryTypeMapper.insert(dictMemoryType);
		
		dictMemoryType.setId(UUID.randomUUID().toString());
		dictMemoryType.setMemoryTypeId("15");
		dictMemoryType.setMemoryTypeName("CDRAM");
		dictMemoryTypeMapper.insert(dictMemoryType);
		
		dictMemoryType.setId(UUID.randomUUID().toString());
		dictMemoryType.setMemoryTypeId("16");
		dictMemoryType.setMemoryTypeName("3DRAM");
		dictMemoryTypeMapper.insert(dictMemoryType);
		
		dictMemoryType.setId(UUID.randomUUID().toString());
		dictMemoryType.setMemoryTypeId("17");
		dictMemoryType.setMemoryTypeName("SDRAM");
		dictMemoryTypeMapper.insert(dictMemoryType);
		
		dictMemoryType.setId(UUID.randomUUID().toString());
		dictMemoryType.setMemoryTypeId("18");
		dictMemoryType.setMemoryTypeName("SGRAM");
		dictMemoryTypeMapper.insert(dictMemoryType);
		
		dictMemoryType.setId(UUID.randomUUID().toString());
		dictMemoryType.setMemoryTypeId("19");
		dictMemoryType.setMemoryTypeName("RDRAM");
		dictMemoryTypeMapper.insert(dictMemoryType);
		
		dictMemoryType.setId(UUID.randomUUID().toString());
		dictMemoryType.setMemoryTypeId("20");
		dictMemoryType.setMemoryTypeName("DDR");
		dictMemoryTypeMapper.insert(dictMemoryType);
		
		dictMemoryType.setId(UUID.randomUUID().toString());
		dictMemoryType.setMemoryTypeId("21");
		dictMemoryType.setMemoryTypeName("DDR2");
		dictMemoryTypeMapper.insert(dictMemoryType);
		
		dictMemoryType.setId(UUID.randomUUID().toString());
		dictMemoryType.setMemoryTypeId("22");
		dictMemoryType.setMemoryTypeName("DDR2 FB-DIMM");
		dictMemoryTypeMapper.insert(dictMemoryType);
		
		dictMemoryType.setId(UUID.randomUUID().toString());
		dictMemoryType.setMemoryTypeId("23");
		dictMemoryType.setMemoryTypeName("DDR3");
		dictMemoryTypeMapper.insert(dictMemoryType);
		
		dictMemoryType.setId(UUID.randomUUID().toString());
		dictMemoryType.setMemoryTypeId("24");
		dictMemoryType.setMemoryTypeName("DDR3");
		dictMemoryTypeMapper.insert(dictMemoryType);
	}
	
	@GetMapping(value = { "/user" })
	@ResponseBody
	public void user() {
		log.info(">>>>>init user");
		User user = new User();
		user.setId(UUID.randomUUID().toString());
		user.setUserName("admin");
		user.setUserPassword("admin@123");
		user.setCrtTime(new Date());
		userMapper.insertSelective(user);
	}
}
