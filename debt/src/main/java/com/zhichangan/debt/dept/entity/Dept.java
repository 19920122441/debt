package com.zhichangan.debt.dept.entity;

import java.math.BigDecimal;

public class Dept {
    private String id;

    private String name;

    private String managerId;

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

    public String getManagerId() {
        return managerId;
    }

    public void setManagerId(String managerId) {
        this.managerId = managerId == null ? null : managerId.trim();
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
        return "Dept{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", managerId='" + managerId + '\'' +
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