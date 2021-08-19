package com.zhichangan.debt.lawCase.entity;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;


public class LawCase {
    @ExcelIgnore
    private String id;
    @ExcelIgnore
    private String owner;
    @ExcelProperty(index = 2)
    private String cardId;
    @ExcelProperty(index = 1)
    private String cardBank;
    @ExcelProperty(index = 3)
    private BigDecimal cardMoney;
    @ExcelProperty(index = 7)
    private String cardOverdueTime;
    @ExcelProperty(index = 8)
    private String cardCreateTime;
    @ExcelProperty(index = 9)
    private String cardCreateBank;
    @ExcelProperty(index = 6)
    private BigDecimal cardMaxReduction;
    @ExcelProperty(index = 11)
    private String lawcaseOwnerId;
    @ExcelProperty(index = 10)
    private String lawcaseOwnerName;
    @ExcelProperty(index = 13)
    private String lawcaseOwnerAge;
    @ExcelProperty(index = 12)
    private String lawcaseOwnerSex;
    @ExcelProperty(index = 14)
    private String lawcaseOwnerAddress;

    @ExcelIgnore
    private String state;
    @ExcelIgnore
    private String nextCallTime;
    @ExcelIgnore
    private String color;
    @ExcelProperty(index = 15)
    private String lawcaseLevel;
    @ExcelProperty(index = 4)
    private BigDecimal cardInterest;
    @ExcelProperty(index = 5)
    private BigDecimal cardLatefee;
    @ExcelIgnore
    private String lastCallTime;
    @ExcelProperty(index = 0)
    private String lawcaseId;
    @ExcelProperty(index = 16)
    private String lawcaseOwnerWork;
    @ExcelProperty(index = 18)
    private String lawcaseOwnerWorkAddress;
    @ExcelProperty(index = 17)
    private String lawcaseOwnerJob;
    @ExcelProperty(index = 20)
    private String cardListAddress;
    @ExcelProperty(index = 19)
    private String cardListDay;
    @ExcelProperty(index = 21)
    private String lawcaseOwnerNowAddress;

    @ExcelProperty(index = 22)
    private String contactsNameOne;
    @ExcelProperty(index = 24)
    private String contactsSexOne;
    @ExcelProperty(index = 23)
    private String contactsRelationshipOne;
    @ExcelProperty(index = 25)
    private String contactsNumberOne;

    @ExcelProperty(index = 26)
    private String contactsNameTwo;
    @ExcelProperty(index = 28)
    private String contactsSexTwo;
    @ExcelProperty(index = 27)
    private String contactsRelationshipTwo;
    @ExcelProperty(index = 29)
    private String contactsNumberTwo;

    @ExcelProperty(index = 30)
    private String contactsNameThree;
    @ExcelProperty(index = 32)
    private String contactsSexThree;
    @ExcelProperty(index = 31)
    private String contactsRelationshipThree;
    @ExcelProperty(index = 33)
    private String contactsNumberThree;



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getCardBank() {
        return cardBank;
    }

    public void setCardBank(String cardBank) {
        this.cardBank = cardBank;
    }

    public BigDecimal getCardMoney() {
        return cardMoney;
    }

    public void setCardMoney(BigDecimal cardMoney) {
        this.cardMoney = cardMoney;
    }

    public String getCardOverdueTime() {
        return cardOverdueTime;
    }

    public void setCardOverdueTime(String cardOverdueTime) {
        this.cardOverdueTime = cardOverdueTime;
    }

    public String getCardCreateTime() {
        return cardCreateTime;
    }

    public void setCardCreateTime(String cardCreateTime) {
        this.cardCreateTime = cardCreateTime;
    }

    public String getCardCreateBank() {
        return cardCreateBank;
    }

    public void setCardCreateBank(String cardCreateBank) {
        this.cardCreateBank = cardCreateBank;
    }

    public BigDecimal getCardMaxReduction() {
        return cardMaxReduction;
    }

    public void setCardMaxReduction(BigDecimal cardMaxReduction) {
        this.cardMaxReduction = cardMaxReduction;
    }

    public String getLawcaseOwnerId() {
        return lawcaseOwnerId;
    }

    public void setLawcaseOwnerId(String lawcaseOwnerId) {
        this.lawcaseOwnerId = lawcaseOwnerId;
    }

    public String getLawcaseOwnerName() {
        return lawcaseOwnerName;
    }

    public void setLawcaseOwnerName(String lawcaseOwnerName) {
        this.lawcaseOwnerName = lawcaseOwnerName;
    }

    public String getLawcaseOwnerAge() {
        return lawcaseOwnerAge;
    }

    public void setLawcaseOwnerAge(String lawcaseOwnerAge) {
        this.lawcaseOwnerAge = lawcaseOwnerAge;
    }

    public String getLawcaseOwnerSex() {
        return lawcaseOwnerSex;
    }

    public void setLawcaseOwnerSex(String lawcaseOwnerSex) {
        this.lawcaseOwnerSex = lawcaseOwnerSex;
    }

    public String getLawcaseOwnerAddress() {
        return lawcaseOwnerAddress;
    }

    public void setLawcaseOwnerAddress(String lawcaseOwnerAddress) {
        this.lawcaseOwnerAddress = lawcaseOwnerAddress;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getNextCallTime() {
        return nextCallTime;
    }

    public void setNextCallTime(String nextCallTime) {
        this.nextCallTime = nextCallTime;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getLawcaseLevel() {
        return lawcaseLevel;
    }

    public void setLawcaseLevel(String lawcaseLevel) {
        this.lawcaseLevel = lawcaseLevel;
    }

    public BigDecimal getCardInterest() {
        return cardInterest;
    }

    public void setCardInterest(BigDecimal cardInterest) {
        this.cardInterest = cardInterest;
    }

    public BigDecimal getCardLatefee() {
        return cardLatefee;
    }

    public void setCardLatefee(BigDecimal cardLatefee) {
        this.cardLatefee = cardLatefee;
    }

    public String getLastCallTime() {
        return lastCallTime;
    }

    public void setLastCallTime(String lastCallTime) {
        this.lastCallTime = lastCallTime;
    }

    public String getLawcaseId() {
        return lawcaseId;
    }

    public void setLawcaseId(String lawcaseId) {
        this.lawcaseId = lawcaseId;
    }

    public String getLawcaseOwnerWork() {
        return lawcaseOwnerWork;
    }

    public void setLawcaseOwnerWork(String lawcaseOwnerWork) {
        this.lawcaseOwnerWork = lawcaseOwnerWork;
    }

    public String getLawcaseOwnerWorkAddress() {
        return lawcaseOwnerWorkAddress;
    }

    public void setLawcaseOwnerWorkAddress(String lawcaseOwnerWorkAddress) {
        this.lawcaseOwnerWorkAddress = lawcaseOwnerWorkAddress;
    }

    public String getLawcaseOwnerJob() {
        return lawcaseOwnerJob;
    }

    public void setLawcaseOwnerJob(String lawcaseOwnerJob) {
        this.lawcaseOwnerJob = lawcaseOwnerJob;
    }

    public String getCardListAddress() {
        return cardListAddress;
    }

    public void setCardListAddress(String cardListAddress) {
        this.cardListAddress = cardListAddress;
    }

    public String getCardListDay() {
        return cardListDay;
    }

    public void setCardListDay(String cardListDay) {
        this.cardListDay = cardListDay;
    }

    public String getLawcaseOwnerNowAddress() {
        return lawcaseOwnerNowAddress;
    }

    public void setLawcaseOwnerNowAddress(String lawcaseOwnerNowAddress) {
        this.lawcaseOwnerNowAddress = lawcaseOwnerNowAddress;
    }

    public String getContactsNameOne() {
        return contactsNameOne;
    }

    public void setContactsNameOne(String contactsNameOne) {
        this.contactsNameOne = contactsNameOne;
    }

    public String getContactsSexOne() {
        return contactsSexOne;
    }

    public void setContactsSexOne(String contactsSexOne) {
        this.contactsSexOne = contactsSexOne;
    }

    public String getContactsRelationshipOne() {
        return contactsRelationshipOne;
    }

    public void setContactsRelationshipOne(String contactsRelationshipOne) {
        this.contactsRelationshipOne = contactsRelationshipOne;
    }

    public String getContactsNumberOne() {
        return contactsNumberOne;
    }

    public void setContactsNumberOne(String contactsNumberOne) {
        this.contactsNumberOne = contactsNumberOne;
    }

    public String getContactsNameTwo() {
        return contactsNameTwo;
    }

    public void setContactsNameTwo(String contactsNameTwo) {
        this.contactsNameTwo = contactsNameTwo;
    }

    public String getContactsSexTwo() {
        return contactsSexTwo;
    }

    public void setContactsSexTwo(String contactsSexTwo) {
        this.contactsSexTwo = contactsSexTwo;
    }

    public String getContactsRelationshipTwo() {
        return contactsRelationshipTwo;
    }

    public void setContactsRelationshipTwo(String contactsRelationshipTwo) {
        this.contactsRelationshipTwo = contactsRelationshipTwo;
    }

    public String getContactsNumberTwo() {
        return contactsNumberTwo;
    }

    public void setContactsNumberTwo(String contactsNumberTwo) {
        this.contactsNumberTwo = contactsNumberTwo;
    }

    public String getContactsNameThree() {
        return contactsNameThree;
    }

    public void setContactsNameThree(String contactsNameThree) {
        this.contactsNameThree = contactsNameThree;
    }

    public String getContactsSexThree() {
        return contactsSexThree;
    }

    public void setContactsSexThree(String contactsSexThree) {
        this.contactsSexThree = contactsSexThree;
    }

    public String getContactsRelationshipThree() {
        return contactsRelationshipThree;
    }

    public void setContactsRelationshipThree(String contactsRelationshipThree) {
        this.contactsRelationshipThree = contactsRelationshipThree;
    }

    public String getContactsNumberThree() {
        return contactsNumberThree;
    }

    public void setContactsNumberThree(String contactsNumberThree) {
        this.contactsNumberThree = contactsNumberThree;
    }

    @Override
    public String toString() {
        return "LawCase{" +
                "id='" + id + '\'' +
                ", owner='" + owner + '\'' +
                ", cardId='" + cardId + '\'' +
                ", cardBank='" + cardBank + '\'' +
                ", cardMoney=" + cardMoney +
                ", cardOverdueTime='" + cardOverdueTime + '\'' +
                ", cardCreateTime='" + cardCreateTime + '\'' +
                ", cardCreateBank='" + cardCreateBank + '\'' +
                ", cardMaxReduction=" + cardMaxReduction +
                ", lawcaseOwnerId='" + lawcaseOwnerId + '\'' +
                ", lawcaseOwnerName='" + lawcaseOwnerName + '\'' +
                ", lawcaseOwnerAge='" + lawcaseOwnerAge + '\'' +
                ", lawcaseOwnerSex='" + lawcaseOwnerSex + '\'' +
                ", lawcaseOwnerAddress='" + lawcaseOwnerAddress + '\'' +
                ", state='" + state + '\'' +
                ", nextCallTime='" + nextCallTime + '\'' +
                ", color='" + color + '\'' +
                ", lawcaseLevel='" + lawcaseLevel + '\'' +
                ", cardInterest=" + cardInterest +
                ", cardLatefee=" + cardLatefee +
                ", lastCallTime='" + lastCallTime + '\'' +
                ", lawcaseId='" + lawcaseId + '\'' +
                ", lawcaseOwnerWork='" + lawcaseOwnerWork + '\'' +
                ", lawcaseOwnerWorkAddress='" + lawcaseOwnerWorkAddress + '\'' +
                ", lawcaseOwnerJob='" + lawcaseOwnerJob + '\'' +
                ", cardListAddress='" + cardListAddress + '\'' +
                ", cardListDay='" + cardListDay + '\'' +
                ", lawcaseOwnerNowAddress='" + lawcaseOwnerNowAddress + '\'' +
                ", contactsNameOne='" + contactsNameOne + '\'' +
                ", contactsSexOne='" + contactsSexOne + '\'' +
                ", contactsRelationshipOne='" + contactsRelationshipOne + '\'' +
                ", contactsNumberOne='" + contactsNumberOne + '\'' +
                ", contactsNameTwo='" + contactsNameTwo + '\'' +
                ", contactsSexTwo='" + contactsSexTwo + '\'' +
                ", contactsRelationshipTwo='" + contactsRelationshipTwo + '\'' +
                ", contactsNumberTwo='" + contactsNumberTwo + '\'' +
                ", contactsNameThree='" + contactsNameThree + '\'' +
                ", contactsSexThree='" + contactsSexThree + '\'' +
                ", contactsRelationshipThree='" + contactsRelationshipThree + '\'' +
                ", contactsNumberThree='" + contactsNumberThree + '\'' +
                '}';
    }
}