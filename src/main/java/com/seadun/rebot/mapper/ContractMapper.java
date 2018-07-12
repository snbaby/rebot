package com.seadun.rebot.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import com.seadun.rebot.entity.Contract;

public interface ContractMapper {
    int deleteByPrimaryKey(String id);

    int insert(Contract record);

    int insertSelective(Contract record);

    Contract selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Contract record);

    int updateByPrimaryKey(Contract record);
    
    List<Contract> select(Contract contract);
    
    List<Contract> selectPage(RowBounds rowBounds,@Param("contract") String contract,@Param("startTime")  String
			startTime,@Param("endTime") String endTime);
    
    int updateStatusUnVerified(@Param("status") String status);
}