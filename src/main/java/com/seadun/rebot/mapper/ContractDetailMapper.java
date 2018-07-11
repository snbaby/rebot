package com.seadun.rebot.mapper;

import java.util.List;

import com.seadun.rebot.entity.ContractDetail;

public interface ContractDetailMapper {
    int deleteByPrimaryKey(String id);

    int insert(ContractDetail record);

    int insertSelective(ContractDetail record);

    ContractDetail selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ContractDetail record);

    int updateByPrimaryKey(ContractDetail record);
    
    List<ContractDetail> select(ContractDetail record);
}