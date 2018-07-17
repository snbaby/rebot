package com.seadun.rebot.mapper;

import com.seadun.rebot.entity.DictMemoryType;

public interface DictMemoryTypeMapper {
    int deleteByPrimaryKey(String id);

    int insert(DictMemoryType record);

    int insertSelective(DictMemoryType record);

    DictMemoryType selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(DictMemoryType record);

    int updateByPrimaryKey(DictMemoryType record);
    
    DictMemoryType selectByPrimaryMemTypeId(String memoryTypeId);
}