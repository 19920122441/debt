package com.zhichangan.debt.note.entity;

import com.zhichangan.debt.contacts.entity.Contacts;

public class Note {
    private String id;

    private String text;

    private String lawcaseId;

    private String contactsId;

    private String time;

    private String userName;
    //方便为前端展示数据，新增属性，代表与本条催记相关的联系人
    private Contacts contacts;


    public Contacts getContacts() {
        return contacts;
    }

    public void setContacts(Contacts contacts) {
        this.contacts = contacts;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text == null ? null : text.trim();
    }

    public String getLawcaseId() {
        return lawcaseId;
    }

    public void setLawcaseId(String lawcaseId) {
        this.lawcaseId = lawcaseId == null ? null : lawcaseId.trim();
    }

    public String getContactsId() {
        return contactsId;
    }

    public void setContactsId(String contactsId) {
        this.contactsId = contactsId == null ? null : contactsId.trim();
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time == null ? null : time.trim();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    @Override
    public String toString() {
        return "Note{" +
                "id='" + id + '\'' +
                ", text='" + text + '\'' +
                ", lawcaseId='" + lawcaseId + '\'' +
                ", contactsId='" + contactsId + '\'' +
                ", time='" + time + '\'' +
                ", userName='" + userName + '\'' +
                ", contacts=" + contacts +
                '}';
    }
}