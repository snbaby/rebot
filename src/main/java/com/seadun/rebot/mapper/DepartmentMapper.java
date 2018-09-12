package com.seadun.rebot.mapper;

import java.util.List;

import com.seadun.rebot.entity.Department;
import com.seadun.rebot.entity.Node;

public interface DepartmentMapper {
    int deleteByPrimaryKey(String id);
    
    //通过id 删除主键为id 或者 parentId like id
    int deleteByid(String id);

    int insert(Department record);

    int insertSelective(Department record);

    Department selectByPrimaryKey(String id);
    
    List<Department> list();
    
    List<Node> selectTree();

    int updateByPrimaryKeySelective(Department record);

    int updateByPrimaryKey(Department record);
}