package com.seadun.rebot.mapper;

import com.seadun.rebot.entity.ContractAllView;

public interface ContractAllViewMapper {
    int insert(ContractAllView record);

    int insertSelective(ContractAllView record);
    
    int selectTotal(ContractAllView contractAllView);
    
    int selectVerified();
}