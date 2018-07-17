package com.seadun.rebot.rest;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.seadun.rebot.constant.RebotExceptionConstants;
import com.seadun.rebot.entity.Computer;
import com.seadun.rebot.entity.ContractDetail;
import com.seadun.rebot.entity.Disk;
import com.seadun.rebot.entity.Memory;
import com.seadun.rebot.entity.Network;
import com.seadun.rebot.entity.RebotException;
import com.seadun.rebot.entity.Video;
import com.seadun.rebot.mapper.ContractDetailMapper;
import com.seadun.rebot.mapper.DiskMapper;
import com.seadun.rebot.mapper.MemoryMapper;
import com.seadun.rebot.mapper.NetworkMapper;
import com.seadun.rebot.mapper.VideoMapper;
import com.seadun.rebot.response.ResponseSuccessResult;
import com.seadun.rebot.service.AgentService;
import com.seadun.rebot.util.Utils;

import lombok.extern.slf4j.Slf4j;


@Controller
@RequestMapping("/api")
@Slf4j
public class AgentController {
	
	
	@Autowired
	private AgentService agentService;
	
	@Autowired
	private ContractDetailMapper contractDetailMapper;
	@Autowired
	private MemoryMapper memoryMapper;
	@Autowired
	private VideoMapper videoMapper;
	@Autowired
	private DiskMapper diskMapper;
	@Autowired
	private NetworkMapper networkMapper;
	
	
	private static final String BIOS = "BIOS";
	private static final String OPERATING_SYSTEM = "OperatingSystem";
	private static final String DISK_DRIVE = "DiskDrive";
	private static final String NETWORK_ADAPTER = "NetworkAdapterConfiguration";
	private static final String PHYSICAL_MEMORY = "PhysicalMemory";
	private static final String VIDEO = "VideoController";
	private static final String PROCESSOR = "Processor";
	
	@PostMapping(value = { "/sbox/data/{hostName}" })
	@ResponseBody
	public ResponseEntity<ResponseSuccessResult> clientData(@PathVariable String hostName,String data, HttpServletRequest req) {
		log.debug(">>>>>待处理的数据:{}",data);
		String hostIP = Utils.getIp(req);
		JSONObject jsonData = JSON.parseObject(data);
		String msgFrom = jsonData.getString("from");
		String uuid = jsonData.getString("uuid");
		
		JSONObject rtJsonObject = new JSONObject();
		
		if (BIOS.equals(msgFrom)) {
			//获取设备序列号
			String biosSn = jsonData.getJSONArray("content").getJSONObject(0).getString("SerialNumber");
			
			Computer computer = new Computer();
			computer.setId(uuid);
			computer.setBiosSn(biosSn);
			computer.setIp(hostIP);
			
			computer.setUptDate(new Date());
			computer.setCrtDate(new Date());
			
			log.debug(">>>>>设备序列号:{}",JSON.toJSONString(computer));
			agentService.save(computer);
			//to-do 存入数据库
		}else if(PROCESSOR.equals(msgFrom)) {
			//cpu
			String cpu = jsonData.getJSONArray("content").getJSONObject(0).getString("Name");
			
			Computer computer = new Computer();
			computer.setId(uuid);
			computer.setCpu(cpu);
			computer.setIp(hostIP);
			
			computer.setCrtDate(new Date());
			computer.setUptDate(new Date());
			
			agentService.save(computer);
		}else if(OPERATING_SYSTEM.equals(msgFrom)) {
			//操作系统
			String opSystem = jsonData.getJSONArray("content").getJSONObject(0).getString("Caption");
			//安装日期
			String opInstallDate = jsonData.getJSONArray("content").getJSONObject(0).getString("InstallDate");
			//計算機名
			String csName = jsonData.getJSONArray("content").getJSONObject(0).getString("CSName");
			
			//to-do 存入数据库
			Computer computer = new Computer();
			computer.setId(uuid);
			computer.setOpSystem(opSystem);
			computer.setOpInstallDate(opInstallDate);
			computer.setIp(hostIP);
			
			computer.setCrtDate(new Date());
			computer.setUptDate(new Date());
			
			log.debug(">>>>>操作系统:{}",JSON.toJSONString(computer));
			agentService.save(computer);
			
			ContractDetail contractDetailParam = new ContractDetail();
			contractDetailParam.setComputerId(uuid);
			List<ContractDetail> contractDetailList = contractDetailMapper.select(contractDetailParam);
			if(contractDetailList.size()!=1) {
				throw new RebotException(RebotExceptionConstants.INTERNAL_SERVER_ERROR_CODE,
						RebotExceptionConstants.INTERNAL_SERVER_ERROR_MESSAGE,
						RebotExceptionConstants.INTERNAL_SERVER_ERROR_HTTP_STATUS);
			}
			ContractDetail contractDetail = contractDetailList.get(0);
			
			if(!csName.equals(contractDetail.getEqNo())) {//判断修改计算机名
				rtJsonObject.put("code", "RENAME_COMPUTER");
				rtJsonObject.put("computer", contractDetail.getEqNo());
			}
		}else if(DISK_DRIVE.equals(msgFrom)) {
			diskMapper.deleteByComputerId(uuid);
			JSONArray jsa = jsonData.getJSONArray("content");
			jsa.forEach(obj->{
				JSONObject jsb = (JSONObject) obj;
				String interfaceType = jsb.getString("InterfaceType");
				String serial = jsb.getString("serial");
				String size = jsb.getString("Size");
				//to-do 存入数据库
				
				Disk disk = new  Disk();
				disk.setId(UUID.randomUUID().toString());
				disk.setComputerId(uuid);
				disk.setDiskInterfaceType(interfaceType);
				disk.setDiskCapacity(size);
				disk.setDiskSn(serial);
				disk.setDiskShellSn(serial);
				
				disk.setCrtTime(new Date());
				disk.setUptTime(new Date());
				
				log.debug(">>>>>磁盘:{}",JSON.toJSONString(disk));
				agentService.save(disk);
			});
		}else if(NETWORK_ADAPTER.equals(msgFrom)) {
			networkMapper.deleteByComputerId(uuid);
			JSONArray jsa = jsonData.getJSONArray("content");
			jsa.forEach(obj->{
				JSONObject jsb = (JSONObject) obj;
				String macAddress = jsb.getString("MACAddress");
				//to-do 存入数据库
				
				Network network = new  Network();
				network.setId(UUID.randomUUID().toString());
				network.setComputerId(uuid);
				network.setMacAddress(macAddress);
				
				network.setCrtTime(new Date());
				network.setUptTime(new Date());
				
				log.debug(">>>>>网卡:{}",JSON.toJSONString(network));
				agentService.save(network);
			});
			
		}else if(PHYSICAL_MEMORY.equals(msgFrom)) {
			memoryMapper.deleteByComputerId(uuid);
			JSONArray jsa = jsonData.getJSONArray("content");
			jsa.forEach(obj->{
				JSONObject jsb = (JSONObject) obj;
				//内存类型PartNumber
				String memType = jsb.getString("MemoryType");
				//内存容量Capacity
				String memCapacity = jsb.getString("Capacity");
				//内存序列号
				String memSn = jsb.getString("SerialNumber");
				//to-do 存入数据库
				Memory memory = new Memory(); 
				memory.setId(UUID.randomUUID().toString());
				memory.setComputerId(uuid);
				memory.setMemType(memType);
				memory.setMemCapacity(memCapacity);
				memory.setMemSn(memSn);
				
				memory.setCrtTime(new Date());
				memory.setUptTime(new Date());
				
				log.debug(">>>>>内存:{}",JSON.toJSONString(memory));
				agentService.save(memory);
			});
		}else if(VIDEO.equals(msgFrom)) {
			videoMapper.deleteByComputerId(uuid);
			JSONArray jsa = jsonData.getJSONArray("content");
			jsa.forEach(obj->{
				JSONObject jsb = (JSONObject) obj;
				//显卡型号
				String videoType = jsb.getString("Caption");
				//to-do 存入数据库
				
				Video video = new Video();
				video.setId(UUID.randomUUID().toString());
				video.setComputerId(uuid);
				//to-do 确定显卡个数
				video.setVideoSn(uuid);
				video.setVideoType(videoType);
				
				video.setCrtTime(new Date());
				video.setUptTime(new Date());
				
				log.debug(">>>>>显卡:{}",JSON.toJSONString(video));
				agentService.save(video);
			});
		}else {
			//非必须采取的指标
			log.debug(">>>>>未采取的指标:{},内容:{}",msgFrom,jsonData);
		}
		
		ResponseSuccessResult responseResult = new ResponseSuccessResult(HttpStatus.OK.value(), "success",rtJsonObject);
		return new ResponseEntity<>(responseResult, HttpStatus.OK);
	}
	
	
	@GetMapping(value = { "/auth/client/token" })
	@ResponseBody
	public Map<String, String> clientToken(@RequestParam String clientId,@RequestParam String secret, HttpServletRequest req) {
		Map<String,String> retMap = new HashMap<>();
		retMap.put("data", "OK");
		retMap.put("status", "200");
		return retMap;
	}
	
	@PostMapping(value = { "/upgrade/{hash}" })
	public void upgrade(@PathVariable String hash, String relPath, HttpServletResponse response,
			HttpServletRequest request) throws Exception {
		response.setStatus(404);
		response.getWriter().print("upgrade.json not found");
	}
}
