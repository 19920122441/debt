package com.zhichangan.debt.user.service.impl;

import com.zhichangan.debt.dept.entity.Dept;
import com.zhichangan.debt.dept.mapper.DeptMapper;
import com.zhichangan.debt.lawCase.mapper.LawCaseMapper;
import com.zhichangan.debt.mail.entity.Mail;
import com.zhichangan.debt.user.entity.User;
import com.zhichangan.debt.user.mapper.UserMapper;
import com.zhichangan.debt.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private LawCaseMapper lawCaseMapper;
    @Autowired
    private DeptMapper deptMapper;
    @Override
    public User login(String userName, String password) {
        User user=userMapper.login( userName,password);
        return user;
    }

    /**
     * 拿到的MailList,其中发件人的名字是空。
     * 所以要将Maillist遍历，在依次根据resource(userID)查找Name（发件人）。
     * 为MaileList补全resourceName属性。
     * @return
     */
    @Override
    public List<Mail> addMailListResourceName(List<Mail> mailList) {
        for(Mail mail:mailList){
            User user = userMapper.selectByPrimaryKey(mail.getResource());
            mail.setResourceName(user.getName());
        }
        return mailList;
    }

    @Override
    public User queryUserById(String owner) {
        User user = userMapper.selectByPrimaryKey(owner);
        return user;
    }
    //根据部门编号，查找到所有在该部门下的员工
    @Override
    public List<User> queryUserByDeptId(String deptId) {
        List<User> userList=userMapper.queryUserByDeptId(deptId);
        return userList;
    }
    //查询到所有的员工的id和姓名
    @Override
    public List<User> queryAllUserIdAndNameNotPublicUser(){
        List<User> userList=userMapper.queryAllUserIdAndNameNotPublicUser();
        return userList;
    }
    //user分页查询

    @Override
    public List<User> pageQuery(String pageNumber, String pageSize) {
        List<User> userList=null;
        Integer intPageNumber=Integer.valueOf(pageNumber);
        Integer intPageSize=Integer.valueOf(pageSize);
        //计算需要掠过的条数
        Integer missNumber=(intPageNumber-1)*intPageSize;
        userList=userMapper.pageQuery(missNumber,intPageSize);
        //查询每个员工今天跟进了多少案件，保存在user中
        //获取当前时间。再从当前时间中获取到今天凌晨0点0分0秒的字符串格式/
        LocalDateTime now=LocalDateTime.now();
        String moring=DateTimeFormatter.ofPattern("yyyy-MM-dd").format(now);
        //需要在遍历中去掉公共账户，不能使用for循环遍历
//        for (User user:userList){
//            Integer todayCallNumber=lawCaseMapper.queryTodayCallNumberByUserId(user.getId(),moring);
//            user.setTodayCallLawCaseNumber(todayCallNumber);
//
//        }
        Iterator<User> iterator = userList.iterator();
        while(iterator.hasNext()){
            //排除公共账户

            User user= iterator.next();
            if(user.getId().equals("cc2380d603454615ba7f036d40584522")){
                iterator.remove();
            }
            //补全今日跟进案件数
            Integer todayCallNumber=lawCaseMapper.queryTodayCallNumberByUserId(user.getId(),moring);
            user.setTodayCallLawCaseNumber(todayCallNumber);
            //补全部门信息
            Dept dept=deptMapper.selectByPrimaryKey(user.getDept());
            user.setDeptName(dept.getName());
        }
        return userList;
    }
    //修改员工信息

    @Override
    public boolean edit(User user) {
        int i = userMapper.updateByPrimaryKeySelective(user);
        if(i>0){
            return true;
        }
        return false;
    }

    @Override
    public Integer delete(String[] ids) {
        Integer count=0;
        for (String id : ids) {
            if(userMapper.deleteByPrimaryKey(id)>0){
                count++;
            }
        }
        return count;
    }

    @Override
    public Boolean add(User user) {
        user.setCreatatime(new SimpleDateFormat("yyyy-MM-dd HH-mm-ss").format(new Date()));
        user.setId(UUID.randomUUID().toString().replace("-",""));
        int i = userMapper.insertSelective(user);
        if(i>0){
            return true;
        }
        return false;
    }

    @Override
    public List<User> query(User user) {
        List<User> userList=userMapper.query(user);
        for(User user1:userList){
            Dept dept = deptMapper.selectByPrimaryKey(user1.getDept());
            user1.setDeptName(dept.getName());
            LocalDate localDate=LocalDate.now();
            Integer integer = lawCaseMapper.queryTodayCallNumberByUserId(user.getId(), DateTimeFormatter.ofPattern("yyyy-MM-dd").format(localDate));
            user1.setTodayCallLawCaseNumber(integer);

        }
        return userList;
    }

    @Override
    public User queryUserByJobNumber(String jobNumber) {
        User user=userMapper.queryUserByJobNumber(jobNumber);
        return user;
    }
}
