package com.zhichangan.debt.vo;

import com.alibaba.excel.annotation.ExcelProperty;

import java.math.BigDecimal;

public class repaymentVo {
    @ExcelProperty(index = 2)
    private BigDecimal Money;
    private String id;
    //委案编号，因为银行不可能知道案件的id
    @ExcelProperty(index = 1)
    private String lawCaseId;
    @ExcelProperty(index = 3)

    private String cardId;

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public BigDecimal getMoney() {
        return Money;
    }

    public void setMoney(BigDecimal money) {
        Money = money;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLawCaseId() {
        return lawCaseId;
    }

    public void setLawCaseId(String lawCaseId) {
        this.lawCaseId = lawCaseId;
    }
}
