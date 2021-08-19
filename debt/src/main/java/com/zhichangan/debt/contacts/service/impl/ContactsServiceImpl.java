package com.zhichangan.debt.contacts.service.impl;

import com.zhichangan.debt.contacts.entity.Contacts;
import com.zhichangan.debt.contacts.mapper.ContactsMapper;
import com.zhichangan.debt.contacts.service.ContactsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Service
public class ContactsServiceImpl implements ContactsService {
    @Autowired
    private ContactsMapper contactsMapper;
    @Override
    public List<Contacts> getContactsByLawCaseId(String id) {
        List<Contacts> contactsList=contactsMapper.getContactsByLawCaseId(id);
        return contactsList;
    }

    @Override
    public Contacts queryContactsById(String contactsId) {
        Contacts contacts = contactsMapper.selectByPrimaryKey(contactsId);
        return contacts;
    }
//根据联系人id，更新状态为无效
    @Override
    public boolean remarkInvalid(String id) {
        Contacts contacts = new Contacts();
        contacts.setId(id);
        contacts.setState("失效");
        int i = contactsMapper.updateByPrimaryKeySelective(contacts);
        if(i>0) {
            return true;
        }else {
            return false;
        }

    }
    //新增联系人

    @Override
    public HashMap<String, Object> add(Contacts contacts) {
        HashMap<String,Object> result=new HashMap<>();
        contacts.setState("有效");
        contacts.setId(UUID.randomUUID().toString().replace("-",""));
        int insert = contactsMapper.insert(contacts);
        if(insert!=0){
            result.put("result",true);
            result.put("contacts",contacts);
        }
        return result;
    }
    //根据案件id删除联系人

    @Override
    public int deleteByLawCaseId(String lawCaseId) {
        int result=contactsMapper.deleteByLawCaseId(lawCaseId);
        return result;
    }
}
