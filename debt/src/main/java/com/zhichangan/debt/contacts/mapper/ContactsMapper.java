package com.zhichangan.debt.contacts.mapper;

import com.zhichangan.debt.contacts.entity.Contacts;

import java.util.List;

public interface ContactsMapper {
    int deleteByPrimaryKey(String id);

    int insert(Contacts record);

    int insertSelective(Contacts record);

    Contacts selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Contacts record);

    int updateByPrimaryKey(Contacts record);

    List<Contacts> getContactsByLawCaseId(String id);

    int deleteByLawCaseId(String lawCaseId);
}