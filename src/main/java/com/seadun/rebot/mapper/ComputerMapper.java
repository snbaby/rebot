package com.seadun.rebot.mapper;

import java.util.List;

import com.seadun.rebot.entity.Computer;

public interface ComputerMapper {
    int deleteByPrimaryKey(String id);
    
    int deleteByPrimaryKeys(List<String> list);

    int insert(Computer record);

    int insertSelective(Computer record);

    Computer selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Computer record);

    int updateByPrimaryKey(Computer record);
}