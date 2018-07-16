package com.seadun.rebot.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import com.seadun.rebot.entity.ContractComputer;

public interface ContractComputerMapper {
    
    List<ContractComputer> selectPage(RowBounds rowBounds,@Param("contractId") String contractId,@Param("computerId") String computerId,@Param("contractStatus") String contractStatus,@Param("contractDetailStatus") String  contractDetailStatus);
    
    List<ContractComputer> select(@Param("contract") String contract,@Param("contractDetailStatus") String  contractDetailStatus,@Param("startTime") String startTime,@Param("endTime") String  endTime);
}