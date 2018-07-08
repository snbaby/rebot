package com.seadun.rebot.rest;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.seadun.rebot.util.Utils;

import lombok.extern.slf4j.Slf4j;


@Controller
@RequestMapping("/api")
@Slf4j
public class AgentController {
	
	private static final String HELPER2017 = "helper2017";
	private static final String MSG_TYPE_EVENT_PLUGIN_POST = "event-plugin-post";
	private static final String SOC_PLUGIN = "soc-plugin-";
	private static final String BIOS = "BIOS";
	private static final String OPERATING_SYSTEM = "OperatingSystem";
	private static final String DISK_DRIVE = "DiskDrive";
	private static final String NETWORK_ADAPTER = "NetworkAdapterConfiguration";
	private static final String PHYSICAL_MEMORY = "PhysicalMemory";
	private static final String VIDEO = "VideoController";
	
	@PostMapping(value = { "/sbox/data/{hostName}" })
	@ResponseBody
	public Map<String, Object> clientData(@PathVariable String hostName,String data, HttpServletRequest req) {
		log.debug(">>>>>待处理的数据:{}",data);
		String hostIP = Utils.getIp(req);
		JSONObject jsonData = JSON.parseObject(data);
		String msgFrom = jsonData.getString("from");
		String content = jsonData.getString("content");
		String uuid = jsonData.getString("uuid");
		if (BIOS.equals(msgFrom)) {
			//获取设备序列号
			String bios_serial_number = jsonData.getJSONArray("content").getJSONObject(0).getString("SerialNumber");
			//to-do 存入数据库
		}else if(OPERATING_SYSTEM.equals(msgFrom)) {
			//操作系统
			String op_system = jsonData.getJSONArray("content").getJSONObject(0).getString("Caption");
			//安装日期
			String op_install_date = jsonData.getJSONArray("content").getJSONObject(0).getString("InstallDate");
			//to-do 存入数据库
		}else if(DISK_DRIVE.equals(msgFrom)) {
			//to-do 暂时不做处理，待刘毅给出解决方案 
		}else if(NETWORK_ADAPTER.equals(msgFrom)) {
			JSONArray jsa = jsonData.getJSONArray("content");
			jsa.forEach(obj->{
				JSONObject jsb = (JSONObject) obj;
				String mac_address = jsb.getString("mac_address");
				//to-do 存入数据库
			});
			
		}else if(PHYSICAL_MEMORY.equals(msgFrom)) {
			JSONArray jsa = jsonData.getJSONArray("content");
			jsa.forEach(obj->{
				JSONObject jsb = (JSONObject) obj;
				//内存类型PartNumber
				String memory_type = jsb.getString("PartNumber");
				//内存容量Capacity
				String memory_capacity = jsb.getString("Capacity");
				//to-do 存入数据库
			});
		}else if(VIDEO.equals(msgFrom)) {
			JSONArray jsa = jsonData.getJSONArray("content");
			jsa.forEach(obj->{
				JSONObject jsb = (JSONObject) obj;
				//显卡型号
				String video_type = jsb.getString("video_type");
				//to-do 存入数据库
			});
		}else {
			//非必须采取的指标
			log.debug(">>>>>非采取的指标:{},内容:{}",msgFrom,jsonData);
		}
		
		Map<String, Object> ret = new HashMap<String, Object>();
		ret.put("code", "200");
		ret.put("checking", "Y");
		return ret;
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
