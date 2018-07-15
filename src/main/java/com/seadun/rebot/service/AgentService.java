package com.seadun.rebot.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.seadun.rebot.constant.RebotExceptionConstants;
import com.seadun.rebot.entity.Computer;
import com.seadun.rebot.entity.Contract;
import com.seadun.rebot.entity.ContractDetail;
import com.seadun.rebot.entity.Disk;
import com.seadun.rebot.entity.Memory;
import com.seadun.rebot.entity.Network;
import com.seadun.rebot.entity.RebotException;
import com.seadun.rebot.entity.Video;
import com.seadun.rebot.mapper.ComputerMapper;
import com.seadun.rebot.mapper.ContractDetailMapper;
import com.seadun.rebot.mapper.ContractMapper;
import com.seadun.rebot.mapper.DiskMapper;
import com.seadun.rebot.mapper.MemoryMapper;
import com.seadun.rebot.mapper.NetworkMapper;
import com.seadun.rebot.mapper.VideoMapper;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AgentService {
	
	@Autowired
	private ComputerMapper computerMapper;
	@Autowired
	private NetworkMapper networkMapper;
	@Autowired
	private MemoryMapper memoryMapper;
	@Autowired
	private VideoMapper videoMapper;
	@Autowired
	private ContractMapper contractMapper;
	@Autowired
	private ContractDetailMapper contractDetailMapper;
	@Autowired
	private DiskMapper diskMapper;
	
	@Transactional
	public void save(Computer computer) {
		String uuid = computer.getId();
		
		if(computerMapper.selectByPrimaryKey(uuid) == null) {//新增，需要判断是否有可
			Contract contractParam = new Contract();
			contractParam.setStatus("YES");
			List<Contract> contractList = contractMapper.select(contractParam);
			if(contractList.isEmpty()) {
				throw new RebotException(RebotExceptionConstants.ASSET_CODE_NOT_EXISTS_ERROR_CODE,
						RebotExceptionConstants.ASSET_CODE_NOT_EXISTS_ERROR_MESSAGE,
						RebotExceptionConstants.ASSET_CODE_NOT_EXISTS_ERROR_HTTP_STATUS);
			}else if(contractList.size()>1) {
				throw new RebotException(RebotExceptionConstants.INTERNAL_SERVER_ERROR_CODE,
						RebotExceptionConstants.INTERNAL_SERVER_ERROR_MESSAGE,
						RebotExceptionConstants.INTERNAL_SERVER_ERROR_HTTP_STATUS);
			}else {
				Contract contractTemp = contractList.get(0);
				ContractDetail contractDetailParam = new ContractDetail();
				contractDetailParam.setContractId(contractTemp.getId());
				contractDetailParam.setComputerId("");
				List<ContractDetail> contractDetailList = contractDetailMapper.select(contractDetailParam);
				
				if(contractDetailList.isEmpty()) {
					throw new RebotException(RebotExceptionConstants.ASSET_CODE_NOT_EXISTS_ERROR_CODE,
							RebotExceptionConstants.ASSET_CODE_NOT_EXISTS_ERROR_MESSAGE,
							RebotExceptionConstants.ASSET_CODE_NOT_EXISTS_ERROR_HTTP_STATUS);
				}else {
					ContractDetail contractDetailTemp = contractDetailList.get(0);
					//to-do 告诉密查客户端修改
					//tell cientIp fix contractDetailTemp.getEqNo()
					contractDetailTemp.setComputerId(computer.getId());
					contractDetailTemp.setUptTime(new Date());
					contractDetailMapper.updateByPrimaryKeySelective(contractDetailTemp);
				}
			}
		}
		computerMapper.insertSelective(computer);
	}
	
	@Transactional
	public void save(Network network) {
		networkMapper.insertSelective(network);
	}
	
	@Transactional
	public void save(Memory memory) {
		memoryMapper.insertSelective(memory);
	}
	
	@Transactional
	public void save(Video video) {
		videoMapper.insertSelective(video);
	}
	
	@Transactional
	public void save(Disk disk) {
		diskMapper.insertSelective(disk);
	}
}
