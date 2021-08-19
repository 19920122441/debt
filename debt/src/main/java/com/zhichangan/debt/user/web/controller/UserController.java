package com.zhichangan.debt.user.web.controller;

import com.zhichangan.debt.dept.entity.Dept;
import com.zhichangan.debt.dept.service.DeptService;
import com.zhichangan.debt.note.entity.Note;
import com.zhichangan.debt.note.service.NoteService;
import com.zhichangan.debt.user.entity.User;
import com.zhichangan.debt.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Controller
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private DeptService deptService;
    @Autowired
    private NoteService noteService;
    //跳转到user下的main
    @RequestMapping(value = "/user/main")
    public String main(Model model){
        List<Dept> deptList=deptService.queryAllDept();
        model.addAttribute("deptList",deptList);
        //从部门中筛选出公共部门然后删除
        Iterator<Dept> iterator = deptList.iterator();
        while(iterator.hasNext()){
            Dept dept=iterator.next();
            if(dept.getName().equals("公共案件账户")){
                iterator.remove();
            }
        }
        List<User> userList = userService.queryAllUserIdAndNameNotPublicUser();
        model.addAttribute("userCount",userList.size());
        return "/user/main";
    }
    //user分页查询
    @RequestMapping(value = "/user/pageQuery")
    @ResponseBody
    public List<User> pageQuery(String pageNumber,String pageSize){
        List<User> userList=userService.pageQuery(pageNumber,pageSize);
        return userList;
    }
    @RequestMapping(value = "/user/login")
    //用户登录
    public String login(Model model,HttpServletRequest request, String userName, String password){
        User user = userService.login(userName, password);
        /**
         * 判断根据用户名和密码是否能在数据库中获取到用户，如果能添加到Session中，做前端获取数据以及令牌二次验证。
         * 如果获取到用户，判断是否为管理员，如果是转发到管理员页面。如果不是，跳转到用户页面。
         * 如果无法获取到用户，转发到登录页面重新登录。
         */
        if(user!=null){
            HttpSession session=request.getSession();
            session.setAttribute("user",user);
            if ("1".equals(user.getRoot())){
                return "workbench/adminIndex";
            }
            return "workbench/index";
        }
        model.addAttribute("message","用户名或密码错误！");
        return "login";
    }

    //根据部门编号查找到部门下所有的员工
    @RequestMapping(value = "/user/queryUserByDeptId")
    @ResponseBody
    public List<User> queryUserByDeptId(String deptId){
        List<User> userList=userService.queryUserByDeptId(deptId);
        return userList;
    }
    //修改员工信息
    @RequestMapping(value = "/user/edit")
    @ResponseBody
    public boolean edit(User user){
        boolean result=userService.edit(user);
        return result;
    }
    //删除员工
    @RequestMapping(value = "/user/delete")
    @ResponseBody
    public Integer delete(String[] ids){
        Integer count=userService.delete(ids);
        return count;
    }
    //新增
    @RequestMapping(value = "/user/add")
    @ResponseBody
    public Boolean add(User user){
        Boolean result=userService.add(user);
        return result;
    }
    //条件查询
    @RequestMapping(value ="/user/query")
    @ResponseBody
    public List<User> query(User user){
        List<User> userList=userService.query(user);
        return userList;
    }
    //获取用户当天的催收记录
    @RequestMapping(value = "/user/userWork")
    @ResponseBody
    public List<Map<String,Object>> userWork(String id){
        LocalDate now=LocalDate.now();
        List<Map<String,Object>> noteList=noteService.userWork(id, DateTimeFormatter.ofPattern("yyyy-MM-dd").format(now));
        return noteList;
    }
    @RequestMapping(value="/user/queryUserByJobNumber")
    @ResponseBody
    public User queryUserByJobNumber(String jobNumber){
        User user=userService.queryUserByJobNumber(jobNumber);
        return user;
    }

}
