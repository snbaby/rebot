package com.seadun.rebot.mapper;

import com.seadun.rebot.entity.Disk;

public interface DiskMapper {
    int deleteByPrimaryKey(String id);

    int insert(Disk record);

    int insertSelective(Disk record);

    Disk selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Disk record);

    int updateByPrimaryKey(Disk record);
}