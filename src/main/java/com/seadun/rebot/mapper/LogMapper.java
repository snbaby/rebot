package com.seadun.rebot.mapper;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.seadun.rebot.entity.Log;

public interface LogMapper {
    int deleteByPrimaryKey(String id);

    int insert(Log record);

    int insertSelective(Log record);

    Log selectByPrimaryKey(String id);
    
    List<Log> selectPage(RowBounds rowBounds);

    int updateByPrimaryKeySelective(Log record);

    int updateByPrimaryKey(Log record);
}