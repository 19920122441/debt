package com.zhichangan.debt.workbench.web.controller;


import com.zhichangan.debt.dic.service.DicService;
import com.zhichangan.debt.lawCase.entity.LawCase;
import com.zhichangan.debt.lawCase.service.LawCaseService;
import com.zhichangan.debt.mail.entity.Mail;
import com.zhichangan.debt.mail.service.MailService;
import com.zhichangan.debt.user.entity.User;
import com.zhichangan.debt.user.service.UserService;
import com.zhichangan.debt.userMailRelationship.service.UserMailRelationship;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class WorkBenchController {


    @Autowired
    private LawCaseService lawCaseService;
    @Autowired
    private MailService mailService;
    @Autowired
    private UserMailRelationship userMailRelationship;
    @Autowired
    private UserService userService;
    @Autowired
    private DicService dicService;


    @RequestMapping(value="/workbench")
    public String wokbench(){
        return "workbench/adminIndex";
    }


    //转发到工作台页面，在这之前需要获取用到的数据
    //当天需要跟进的案件，未读邮件，历史以及当月佣金及绩效
    @RequestMapping("/workbench/main")
    public String workbechMain(HttpSession session,Model model){
        User user= (User) session.getAttribute("user");



        //获取当天需要跟进的案件，并添加到model
        List<LawCase> lawCaseList=lawCaseService.queryTodayCallLawCase(session);
        model.addAttribute("lawCaseList",lawCaseList);
        //获取未读邮件
        List<Mail> mailList=mailService.queryNotReadMail(session);
        //获取未读邮件的发件人信息
        List<Mail> resultMailList=userService.addMailListResourceName(mailList);
        model.addAttribute("mailList",resultMailList);
        return "workbench/main";
    }
}
