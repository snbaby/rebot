package com.seadun.rebot.mapper;

import java.util.List;

import com.seadun.rebot.entity.Contract;

public interface ContractMapper {
    int deleteByPrimaryKey(String id);

    int insert(Contract record);

    int insertSelective(Contract record);

    Contract selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Contract record);

    int updateByPrimaryKey(Contract record);
    
    List<Contract> select(Contract contract);
}