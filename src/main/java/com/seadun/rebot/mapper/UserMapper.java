package com.seadun.rebot.mapper;

import java.util.List;

import com.seadun.rebot.entity.User;

public interface UserMapper {
    int deleteByPrimaryKey(String id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(String id);
    
    List<User> select(User user);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
}