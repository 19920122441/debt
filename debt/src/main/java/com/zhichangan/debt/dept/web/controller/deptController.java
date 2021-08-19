package com.zhichangan.debt.dept.web.controller;

import com.zhichangan.debt.dept.entity.Dept;
import com.zhichangan.debt.dept.mapper.DeptMapper;
import com.zhichangan.debt.dept.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class deptController {
    @Autowired
    private DeptService deptService;

    @RequestMapping(value = "/dept/queryAllDept")
    @ResponseBody
    public List<Dept> queryAllDeptName(){
        List<Dept> result=deptService.queryAllDept();
        return result;
    }
}
