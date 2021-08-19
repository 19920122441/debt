package com.zhichangan.debt.mail.web.controller;

import com.zhichangan.debt.mail.entity.Mail;
import com.zhichangan.debt.mail.service.MailService;
import com.zhichangan.debt.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Controller
public class MailController {
    @Autowired
    private MailService mailService;

    @RequestMapping(value = "/mail/main")
    public ModelAndView mailMain(HttpServletRequest request){
        ModelAndView modelAndView=new ModelAndView();
        //从session中获取当前登录用户id，并查找属于该用户的邮件数量
        User user= (User) request.getSession(false).getAttribute("user");
        Map<String,Integer> countMap=mailService.mailMain(user.getId());
        modelAndView.setViewName("/mail/main");
        modelAndView.addObject("mailCount",countMap.get("mailCount"));
        modelAndView.addObject("notReadCount",countMap.get("notReadCount"));
        return modelAndView;
    }
    //通过邮件id参数。获取mail。以及该mail的所有收件人姓名
    //并且转发到邮件的detail页面
    @RequestMapping(value = "/mail/detail")
    public ModelAndView mailDetail(String mailId, HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        User user= (User) request.getSession(false).getAttribute("user");
        Map<String, Object> resultMap = mailService.detailMail(mailId, user.getId());
        modelAndView.addObject("mail", resultMap.get("mail"));
        modelAndView.addObject("mailUserNameList").addObject("mailUserNameList", resultMap.get("mailUserNameList"));
        return modelAndView;
    }

    //下载附件
    @RequestMapping(value = "/mail/download")
    @ResponseBody
    public String download(String filePath, HttpServletResponse response)  {
       String result=mailService.downloadFile(filePath,response);
       return result;

    }
    //删除邮件
    @RequestMapping(value = "/mail/delete")
    @ResponseBody
    public String delete(String mailId){
        boolean result=mailService.delete(mailId);
        if(result){
            return "删除成功！";
        }
        return "删除失败!";
    }

    //分页查询
    @RequestMapping(value = "/mail/pageQuery")
    @ResponseBody
    public List<Mail> pageQuery(String pageSize,String pageNumber,HttpServletRequest request){
        User user= (User) request.getSession(false).getAttribute("user");
        List<Mail> mailList=mailService.pageQueryMailByUserId(pageSize,pageNumber,user.getId());
        return mailList;
    }

    @RequestMapping(value = "/mail/getMailAsUser")
    @ResponseBody
    public List<Map<String,Object>> getMailAsUser(){
        List<Map<String,Object>> result=mailService.getMailAsUser();
        return result;
    }
    //发送邮件
    @RequestMapping(value = "/mail/sendMail")
    @ResponseBody
    public Map<String,Object> sendMail(HttpServletRequest request,String[] asUser,MultipartFile myFile,String title,String text) throws IOException {
        Map<String,Object> resultMap=null;
        resultMap=mailService.sendMail(request,asUser,myFile,title,text);
        return resultMap;
    }
    //查询是本人发送的邮件
    @RequestMapping(value = "/mail/queryMySendMail")
    @ResponseBody
    public List<Mail> queryMySendMail(HttpServletRequest request){
        User user= (User) request.getSession(false).getAttribute("user");
        List<Mail> resultList=mailService.queryMySendMail(user.getId(),user.getName());
        return resultList;
    }
    //邮件的模糊查询
    @RequestMapping(value = "/mail/likeQuery")
    @ResponseBody
    public List<Mail> likeQuery(HttpServletRequest request,String text){
        User user= (User) request.getSession(false).getAttribute("user");
        List<Mail> mailList=mailService.likeQuery(user.getId(),user.getName(),text);
        return mailList;
    }
}