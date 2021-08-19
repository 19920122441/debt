package com.zhichangan.debt.userMailRelationship.service.impl;

import com.zhichangan.debt.userMailRelationship.mapper.UserMailRelationshipMapper;
import com.zhichangan.debt.userMailRelationship.service.UserMailRelationship;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserMailRelationshipImpl implements UserMailRelationship {
    @Autowired
    private UserMailRelationshipMapper userMailRelationshipMapper;
    //根据邮件id和用户id删除一条记录
    @Override
    public boolean deleteByUserIdAndMailId(String mailId, String id) {
        boolean result=true;
        int i =userMailRelationshipMapper.deleteByUserIdAndMailId(mailId,id);
        if (i<1){
            result=false;
        }
        return result;
    }
}
