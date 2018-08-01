package com.seadun.rebot.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.seadun.rebot.entity.ContractDetail;

public interface ContractDetailMapper {
    int deleteByPrimaryKey(String id);

    int insert(ContractDetail record);

    int insertSelective(ContractDetail record);

    ContractDetail selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ContractDetail record);

    int updateByPrimaryKey(ContractDetail record);
    
    List<ContractDetail> select(ContractDetail record);
    
    long selectCount(@Param("contractDetail") ContractDetail record);
    
    long selectCountTime(@Param("startTime") String startTime,@Param("endTime") String endTime,@Param("status") String status);
    
    long selectCountBefore(@Param("contractDetail") ContractDetail record,@Param("uptTime") String uptTime);
    
}