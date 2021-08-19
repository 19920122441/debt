package com.zhichangan.debt.repayment.entity;

import java.math.BigDecimal;

public class Repayment {
    private String id;

    private String lawcaseId;

    private BigDecimal money;

    private String cardId;

    private String time;

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

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId == null ? null : cardId.trim();
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time == null ? null : time.trim();
    }
}