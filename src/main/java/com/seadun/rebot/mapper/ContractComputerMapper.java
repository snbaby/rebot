package com.seadun.rebot.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import com.seadun.rebot.entity.ContractComputer;

public interface ContractComputerMapper {
    int insert(ContractComputer record);

    int insertSelective(ContractComputer record);
    
    List<ContractComputer> selectPage(RowBounds rowBounds,@Param("computerId") String computerId,@Param("contractStatus") String contractStatus,@Param("contractDetailStatus") String  contractDetailStatus);
}