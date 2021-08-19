package com.zhichangan.debt.sal.web.controller;

import com.zhichangan.debt.dept.entity.Dept;
import com.zhichangan.debt.dept.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.util.List;

@Controller
public class SalController {
    @Autowired
    private DeptService deptService;
    @RequestMapping(value = "/sal/main")
    public ModelAndView main(){
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.setViewName("sal/main");
        //获取所有的部门
        List<Dept> depts = deptService.queryAllDept();
        modelAndView.addObject("deptList",depts);
        //计算所有部门当月总业绩
        BigDecimal bigDecimal=new BigDecimal(0);
        for(Dept dept:depts)
        {
            bigDecimal=bigDecimal.add(dept.getAch1());
        }
        modelAndView.addObject("allAch",bigDecimal);
        return modelAndView;
    }
}
