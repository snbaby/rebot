package com.seadun.rebot.mapper;

import java.util.List;

import com.seadun.rebot.entity.Memory;

public interface MemoryMapper {
    int deleteByPrimaryKey(String id);

    int insert(Memory record);

    int insertSelective(Memory record);

    Memory selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Memory record);

    int updateByPrimaryKey(Memory record);
    
    List<Memory> select(Memory memory);
}