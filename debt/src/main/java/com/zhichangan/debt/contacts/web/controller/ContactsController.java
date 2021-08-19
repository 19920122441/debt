package com.zhichangan.debt.contacts.web.controller;

import com.zhichangan.debt.contacts.entity.Contacts;
import com.zhichangan.debt.contacts.service.ContactsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
public class ContactsController {
    @Autowired
    private ContactsService contactsService;



    //标记联系人为失效
    @RequestMapping("/contacts/remarkInvalid")
    @ResponseBody
    public Object remarkInvalid(String id){
        boolean result=contactsService.remarkInvalid(id);
        HashMap<String, Boolean> map = new HashMap<>();
        map.put("result",result);
        return map;
    }
    //新增联系人
    @RequestMapping("/contacts/add")
    @ResponseBody
    public Map<String,Object> add(Contacts contacts){
        HashMap<String,Object> resultMap=new HashMap<>();
        resultMap=contactsService.add(contacts);
        return resultMap;

    }
}
