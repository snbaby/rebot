package com.seadun.rebot.mapper;

import java.util.List;

import com.seadun.rebot.entity.Network;

public interface NetworkMapper {
    int deleteByPrimaryKey(String id);

    int insert(Network record);

    int insertSelective(Network record);

    Network selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Network record);

    int updateByPrimaryKey(Network record);
    
    List<Network> select(Network network);
}