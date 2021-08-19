package com.zhichangan.debt.userMailRelationship.web.controller;

import com.zhichangan.debt.user.entity.User;
import com.zhichangan.debt.userMailRelationship.service.UserMailRelationship;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
public class UserMailRelationshipController {
    @Autowired
    private UserMailRelationship userMailRelationship;

    //根据邮件id和用户id删除一条记录
    @RequestMapping(value = "/userMailRelationship/delete")
    @ResponseBody
    public Map<String,Boolean> delete(String mailId, HttpServletRequest request){
        Map<String, Boolean> resultMap=new HashMap<>();
        User user= (User) request.getSession(false).getAttribute("user");
        boolean result=userMailRelationship.deleteByUserIdAndMailId(mailId,user.getId());
        resultMap.put("success",result);
        return resultMap;
    }
}
