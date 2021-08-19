package com.zhichangan.debt.dept.mapper;

import com.zhichangan.debt.dept.entity.Dept;

import java.util.List;

public interface DeptMapper {
    int deleteByPrimaryKey(String id);

    int insert(Dept record);

    int insertSelective(Dept record);

    Dept selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Dept record);

    int updateByPrimaryKey(Dept record);

    List<Dept> queryAllDept();


}