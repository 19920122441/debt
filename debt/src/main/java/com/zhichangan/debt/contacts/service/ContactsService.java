package com.zhichangan.debt.contacts.service;

import com.zhichangan.debt.contacts.entity.Contacts;

import java.util.HashMap;
import java.util.List;

public interface ContactsService {
    List<Contacts> getContactsByLawCaseId(String id);

    Contacts queryContactsById(String contactsId);

    boolean remarkInvalid(String id);

    HashMap<String, Object> add(Contacts contacts);

    int deleteByLawCaseId(String lawCaseId);
}
