package com.zhichangan.debt.lawCase.web.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zhichangan.debt.lawCase.entity.LawCase;
import com.zhichangan.debt.lawCase.service.LawCaseService;
import com.zhichangan.debt.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.ParseException;
import java.util.*;

@Controller
public class LawCaseController {

    @Autowired
    private LawCaseService lawCaseService;

// * 根据id查找案件，以及案件催收员的信息
//     * 与案件相关的联系人 催收记录 地址 同催案件
//     * 并且转发到详情展示页。
    @RequestMapping(value = "/lawCase/detail")
    @ResponseBody
    public ModelAndView lawCaseDetail(String id) throws ParseException {
        ModelAndView modelAndView=lawCaseService.detail(id);
        modelAndView.setViewName("lawCase/detail");
        return modelAndView;
    }

    //修改案件的状态
    @RequestMapping(value = "/lawCase/updateState")
    @ResponseBody
    public Map<String,Object> updateState(String lawCaseId,String state){
        Map<String,Object> resultMap=new HashMap<>();
        boolean result=lawCaseService.updateState(lawCaseId,state);
        resultMap.put("success",result);
        return resultMap;
    }
    //转发到案件公海页面,获取到案件总个数，
    @RequestMapping(value = "/lawCase/publicLawcase")
    public String publicLawcase(Model model){
        Integer count=lawCaseService.publicLawCase();
        model.addAttribute("lawcaseCount",count);


        return "lawCase/publicLawcase";
    }
    //案件分页查询
    @RequestMapping(value = "/lawCase/page")
    @ResponseBody
    public List<LawCase> page(String pageSize,String pageNumber){
        List<LawCase> lawCaseList=lawCaseService.page(pageSize,pageNumber);
        return lawCaseList;
    }
    //按条件查询
    @RequestMapping(value = "/lawCase/query")
    @ResponseBody
    public Map<String,Object> query(LawCase lawCase,String minMoney,String maxMoney,String notCallTime) throws ParseException {
        System.out.println(lawCase);
        List<LawCase> result=null;
        result=lawCaseService.query(lawCase,minMoney,maxMoney,notCallTime);
        System.out.println(result);
        Map<String,Object> resultMap=new HashMap<>();
        resultMap.put("lawCaseList", result);
        resultMap.put("count",result.size());
        return resultMap;
    }
    //根据id删除案件
    @RequestMapping( value = "/lawCase/delete")
    @ResponseBody
    public void delete(String lawCaseId){
        lawCaseService.delete(lawCaseId);
    }
    //更改案件下次跟进时间,并且将案件状态设置为跟进
    @RequestMapping( value = "/lawCase/updateNextCallTime")
    @ResponseBody
    public Map<String,Object> updateNextCallTime(String lawCaseId,String nextCallTime){
        Map<String,Object> result=new HashMap<>();
        LawCase lawCase=new LawCase();
        lawCase.setId(lawCaseId);
        lawCase.setNextCallTime(nextCallTime);
        lawCase.setState("跟进");
        boolean b = lawCaseService.updateSelective(lawCase);
        result.put("success",b);
        return result;
    }
    //新增单条案件
    @RequestMapping( value = "lawCase/add")
    @ResponseBody
    public Map<String, Boolean> add(LawCase lawCase,HttpServletRequest request){
        boolean result=lawCaseService.add(lawCase,request);
        Map<String,Boolean> resultMap=new HashMap<>();
        resultMap.put("success",result);
        return resultMap;
    }
    //根据上传的表格文件，解析然后批量新增。
    @RequestMapping(value = "/lawCase/multiAdd")
    @ResponseBody
    public Map<String,Object> multiAdd(@RequestParam("file") MultipartFile file,HttpServletRequest request) throws IOException {
        boolean result=lawCaseService.multiAdd(request,file);
        Map<String,Object> resultMap=new HashMap<String,Object>();
        resultMap.put("success",result);

        return resultMap;
    }
    //为案件分配催收员
    @RequestMapping(value = "/lawCase/editLawCaseOwner")
    @ResponseBody
    public Integer editLawCaseOwner(String ids, String userId, HttpServletRequest request) throws JsonProcessingException {
        Map<String,Object> result=new HashMap<>();
        ObjectMapper objectMapper=new ObjectMapper();
        String[] lawCaseIds = objectMapper.readValue(ids, String[].class);

        int count=lawCaseService.editLawCaseOwner(lawCaseIds,userId,request);

        return count;
    }
    //自动分配案件
    //将所有未分配，即在公共账户的案件，平均分配给所有员工
    @RequestMapping(value = "/lawCase/autoEditLawCaseOwner")
    @ResponseBody
    public Integer autoEditLawCaseOwner(HttpServletRequest request){
        Integer count=lawCaseService.autoEditLawCaseOwner2(request);
        return count;
    }

    //标记案件暗夜
    @RequestMapping(value = "/lawCase/markColor")
    @ResponseBody
    public Map<String,Object> markColor(String lawCaseId,String color,HttpServletRequest request){
        Map<String,Object> resultMap=null;
        resultMap=lawCaseService.markColor(lawCaseId,color,request);
        return resultMap;
    }
    //转发到我的案件页面,获取到案件总个数，
    @RequestMapping(value = "/lawCase/myLawcase")
    public String myLawcase(Model model,HttpServletRequest request){
        User user= (User) request.getSession(false).getAttribute("user");

        Integer count=lawCaseService.myLawCase(user.getId());
        model.addAttribute("lawcaseCount",count);


        return "lawCase/myLawcase";
    }
    //我的案件分页查询
    @ResponseBody
    @RequestMapping(value = "/lawCase/pageByOwner")
    public List<LawCase> pageByOwner(String pageSize,String pageNumber,HttpServletRequest request){
        User user = (User) request.getSession(false).getAttribute("user");
        List<LawCase> lawCaseList=lawCaseService.pageByOwner(pageSize,pageNumber,user.getId());
        return lawCaseList;
    }
    //模糊查询，返回符合条件的案件以及案件个数
    @RequestMapping(value = "/lawCase/likeQuery")
    @ResponseBody
    public Map<String,Object> likeQuery(String text){
        Map<String,Object> resultMap=lawCaseService.likeQuery(text);
        return resultMap;
    }
    //还款
    @RequestMapping(value = "/lawCase/repayment")
    @ResponseBody
    public void repayment(MultipartFile file) throws IOException {
        lawCaseService.repayment(file);
    }
}
