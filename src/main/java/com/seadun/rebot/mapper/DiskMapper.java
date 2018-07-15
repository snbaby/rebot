package com.seadun.rebot.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import com.seadun.rebot.entity.Disk;

public interface DiskMapper {
    int deleteByPrimaryKey(String id);

    int deleteByComputerId(String computerId);
    
    int insert(Disk record);

    int insertSelective(Disk record);

    Disk selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Disk record);

    int updateByPrimaryKey(Disk record);
    
    List<Disk> selectPage(RowBounds rowBounds,@Param("computerId") String computerId);
    
    List<Disk> select(Disk disk);
}