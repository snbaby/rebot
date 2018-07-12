package com.seadun.rebot.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.seadun.rebot.constant.RebotConstants;
import com.seadun.rebot.entity.Contract;
import com.seadun.rebot.mapper.ContractMapper;

@Service
public class ContractService {
	@Autowired
	private ContractMapper contractMapper;
	
	@Transactional
	public void statusChange(String id,String status) {
		if(RebotConstants.CONTRACT_VERIFIED.equals(status)) {//开启
			contractMapper.updateStatusUnVerified(RebotConstants.CONTRACT_UNVERIFIED);
			Contract contract = new Contract();
			contract.setId(id);
			contract.setStatus(status);
			contract.setUptTime(new Date());
			contractMapper.updateByPrimaryKeySelective(contract);
		}else {//关闭
			contractMapper.updateStatusUnVerified(RebotConstants.CONTRACT_UNVERIFIED);
		}
	}
}
