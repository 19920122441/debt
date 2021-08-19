package com.zhichangan.debt.contacts.entity;

public class Contacts {
    private String id;

    private String lawcaseId;

    private String name;

    private String sex;

    private String relationship;

    private String state;

    private String number;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getLawcaseId() {
        return lawcaseId;
    }

    public void setLawcaseId(String lawcaseId) {
        this.lawcaseId = lawcaseId == null ? null : lawcaseId.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex == null ? null : sex.trim();
    }

    public String getRelationship() {
        return relationship;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship == null ? null : relationship.trim();
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state == null ? null : state.trim();
    }

    public String getNumber() {
        return number;
    }


    public void setNumber(String number) {
        this.number = number == null ? null : number.trim();
    }

    @Override
    public String toString() {
        return "Contacts{" +
                "id='" + id + '\'' +
                ", lawcaseId='" + lawcaseId + '\'' +
                ", name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                ", relationship='" + relationship + '\'' +
                ", state='" + state + '\'' +
                ", number='" + number + '\'' +
                '}';
    }
}