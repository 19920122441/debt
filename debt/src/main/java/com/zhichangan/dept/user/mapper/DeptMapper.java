package com.zhichangan.dept.user.mapper;

import com.zhichangan.debt.dept.entity.Dept;

public interface DeptMapper {
    int deleteByPrimaryKey(String id);

    int insert(Dept record);

    int insertSelective(Dept record);

    Dept selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Dept record);

    int updateByPrimaryKey(Dept record);
}