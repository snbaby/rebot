package com.seadun.rebot.mapper;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.seadun.rebot.entity.DetectionAccount;

public interface DetectionAccountMapper {
    int deleteByPrimaryKey(String id);

    int insert(DetectionAccount record);

    int insertSelective(DetectionAccount record);

    DetectionAccount selectByPrimaryKey(String id);
    
    List<DetectionAccount> selectAll(RowBounds rowBounds);

    int updateByPrimaryKeySelective(DetectionAccount record);

    int updateByPrimaryKey(DetectionAccount record);
}