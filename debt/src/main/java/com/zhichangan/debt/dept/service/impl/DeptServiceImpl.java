package com.zhichangan.debt.dept.service.impl;

import com.zhichangan.debt.dept.entity.Dept;
import com.zhichangan.debt.dept.mapper.DeptMapper;
import com.zhichangan.debt.dept.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeptServiceImpl implements DeptService {
    @Autowired
    private DeptMapper deptMapper;
    @Override
    public List<Dept> queryAllDept() {
        List<Dept> result=deptMapper.queryAllDept();
        return result;
    }
}
