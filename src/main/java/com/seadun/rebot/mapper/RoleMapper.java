package com.seadun.rebot.mapper;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.seadun.rebot.entity.Role;

public interface RoleMapper {
    int deleteByPrimaryKey(String id);

    int insert(Role record);

    int insertSelective(Role record);

    Role selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);
    
    List<Role> selectPage(RowBounds rowBounds);
    
    List<Role> list();
}