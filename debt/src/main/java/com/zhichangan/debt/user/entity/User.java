package com.zhichangan.debt.user.entity;

import java.math.BigDecimal;

public class User {
    private String id;

    private String name;

    private String dept;

    private String root;

    private String job;

    private String userName;

    private String password;

    private String jobNumber;

    private String phone;

    private String creatatime;

    private String sex;

    private Integer age;

    private String cardid;

    private Integer todayCallLawCaseNumber;

    private String deptName;
    private BigDecimal com1;
    private BigDecimal com2;
    private BigDecimal com3;
    private BigDecimal com4;
    private BigDecimal com5;
    private BigDecimal ach1;
    private BigDecimal ach2;
    private BigDecimal ach3;
    private BigDecimal ach4;
    private BigDecimal ach5;

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public Integer getTodayCallLawCaseNumber() {
        return todayCallLawCaseNumber;
    }

    public void setTodayCallLawCaseNumber(Integer todayCallLawCaseNumber) {
        this.todayCallLawCaseNumber = todayCallLawCaseNumber;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept == null ? null : dept.trim();
    }

    public String getRoot() {
        return root;
    }

    public void setRoot(String root) {
        this.root = root == null ? null : root.trim();
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job == null ? null : job.trim();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getJobNumber() {
        return jobNumber;
    }

    public void setJobNumber(String jobNumber) {
        this.jobNumber = jobNumber == null ? null : jobNumber.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getCreatatime() {
        return creatatime;
    }

    public void setCreatatime(String creatatime) {
        this.creatatime = creatatime == null ? null : creatatime.trim();
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex == null ? null : sex.trim();
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getCardid() {
        return cardid;
    }

    public void setCardid(String cardid) {
        this.cardid = cardid == null ? null : cardid.trim();
    }

    public BigDecimal getCom1() {
        return com1;
    }

    public void setCom1(BigDecimal com1) {
        this.com1 = com1;
    }

    public BigDecimal getCom2() {
        return com2;
    }

    public void setCom2(BigDecimal com2) {
        this.com2 = com2;
    }

    public BigDecimal getCom3() {
        return com3;
    }

    public void setCom3(BigDecimal com3) {
        this.com3 = com3;
    }

    public BigDecimal getCom4() {
        return com4;
    }

    public void setCom4(BigDecimal com4) {
        this.com4 = com4;
    }

    public BigDecimal getCom5() {
        return com5;
    }

    public void setCom5(BigDecimal com5) {
        this.com5 = com5;
    }

    public BigDecimal getAch1() {
        return ach1;
    }

    public void setAch1(BigDecimal ach1) {
        this.ach1 = ach1;
    }

    public BigDecimal getAch2() {
        return ach2;
    }

    public void setAch2(BigDecimal ach2) {
        this.ach2 = ach2;
    }

    public BigDecimal getAch3() {
        return ach3;
    }

    public void setAch3(BigDecimal ach3) {
        this.ach3 = ach3;
    }

    public BigDecimal getAch4() {
        return ach4;
    }

    public void setAch4(BigDecimal ach4) {
        this.ach4 = ach4;
    }

    public BigDecimal getAch5() {
        return ach5;
    }

    public void setAch5(BigDecimal ach5) {
        this.ach5 = ach5;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", dept='" + dept + '\'' +
                ", root='" + root + '\'' +
                ", job='" + job + '\'' +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", jobNumber='" + jobNumber + '\'' +
                ", phone='" + phone + '\'' +
                ", creatatime='" + creatatime + '\'' +
                ", sex='" + sex + '\'' +
                ", age=" + age +
                ", cardid='" + cardid + '\'' +
                ", todayCallLawCaseNumber=" + todayCallLawCaseNumber +
                ", deptName='" + deptName + '\'' +
                ", com1=" + com1 +
                ", com2=" + com2 +
                ", com3=" + com3 +
                ", com4=" + com4 +
                ", com5=" + com5 +
                ", ach1=" + ach1 +
                ", ach2=" + ach2 +
                ", ach3=" + ach3 +
                ", ach4=" + ach4 +
                ", ach5=" + ach5 +
                '}';
    }
}