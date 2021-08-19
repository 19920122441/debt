package com.zhichangan.debt.mail.mapper;

import com.zhichangan.debt.mail.entity.Mail;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MailMapper {
    int deleteByPrimaryKey(String id);

    int insert(Mail record);

    int insertSelective(Mail record);

    Mail selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Mail record);

    int updateByPrimaryKey(Mail record);

    List<Mail> queryNotReadMail(@Param("userId") String id,@Param("state") String state);


    List<Mail> queryByResource(String id);
}