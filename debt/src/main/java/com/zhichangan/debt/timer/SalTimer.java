package com.zhichangan.debt.timer;

import com.zhichangan.debt.dept.entity.Dept;
import com.zhichangan.debt.dept.mapper.DeptMapper;
import com.zhichangan.debt.user.entity.User;
import com.zhichangan.debt.user.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

//每月一号定时任务
@Component
public class SalTimer {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private DeptMapper deptMapper;
    public void timerRun() throws ParseException {
        Timer timer=new Timer();
        TimerTask timerTask=new TimerTask() {
            @Override
            public void run() {
                System.out.println("run!");
                Calendar calendar=Calendar.getInstance();
                int day=calendar.get(Calendar.DAY_OF_MONTH);
                if(day==1){
                    //将所有员工的业绩（ach）和佣金（com）向前移动1
                    //查询出所有员工
                    List<User> userList =userMapper.queryAllUser();
                    for(User user:userList){
                        user.setCom5(user.getCom4());
                        user.setAch5(user.getAch4());

                        user.setCom4(user.getCom3());
                        user.setAch4(user.getAch3());

                        user.setCom3(user.getCom2());
                        user.setAch3(user.getAch2());

                        user.setCom2(user.getCom1());
                        user.setAch2(user.getAch1());

                        user.setCom1(new BigDecimal(0));
                        user.setAch1(new BigDecimal(0));

                        userMapper.updateByPrimaryKeySelective(user);
                    }
                    //将所有部门的业绩（ach）和佣金（com）向前移动1
                    //获取所有的部门
                    List<Dept> deptList=deptMapper.queryAllDept();
                    for(Dept dept:deptList){
                        dept.setCom5(dept.getCom4());
                        dept.setAch5(dept.getAch4());
                        dept.setCom4(dept.getCom3());
                        dept.setAch4(dept.getAch3());
                        dept.setCom3(dept.getCom2());
                        dept.setAch3(dept.getAch2());
                        dept.setCom2(dept.getCom1());
                        dept.setAch2(dept.getAch1());
                        dept.setCom1(new BigDecimal(0));
                        dept.setAch1(new BigDecimal(0));
                        deptMapper.updateByPrimaryKeySelective(dept);

                    }
                }
            }
        };
        SimpleDateFormat std=new SimpleDateFormat("yyyy-MM-01");
        Date startTime=new SimpleDateFormat("yyyy-MM-dd").parse(std.format(new Date()));
        startTime.setMonth(startTime.getMonth()+1);
        timer.schedule(timerTask,startTime,1000*60*60*24);
    }
}
