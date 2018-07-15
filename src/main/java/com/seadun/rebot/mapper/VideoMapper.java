package com.seadun.rebot.mapper;

import java.util.List;

import com.seadun.rebot.entity.Video;

public interface VideoMapper {
    int deleteByPrimaryKey(String id);

    int insert(Video record);

    int insertSelective(Video record);

    Video selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Video record);

    int updateByPrimaryKey(Video record);
    
    List<Video> select(Video video);
}