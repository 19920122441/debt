package com.zhichangan.debt.address.entity;

public class Address {
    private String id;

    private String address;

    private String name;

    private String state;

    private String lawcaseId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state == null ? null : state.trim();
    }

    public String getLawcaseId() {
        return lawcaseId;
    }

    public void setLawcaseId(String lawcaseId) {
        this.lawcaseId = lawcaseId;
    }



    @Override
    public String toString() {
        return "Address{" +
                "id='" + id + '\'' +
                ", address='" + address + '\'' +
                ", name='" + name + '\'' +
                ", state='" + state + '\'' +
                ", lawcaseId='" + lawcaseId + '\'' +
                '}';
    }
}