package com.ayc.service.app.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class AycFinancialEntity implements Serializable{

    private static final long serialVersionUID = 68082309927326193L;

    private Integer id;

    private Byte action;

    private Byte payType;

    private String payAccount;

    private String payCode;

    private BigDecimal cash;

    private Integer uid;

    private Date createdAt;

    private Date updatedAt;

    private BigDecimal nowBalance;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Byte getAction() {
        return action;
    }

    public void setAction(Byte action) {
        this.action = action;
    }

    public Byte getPayType() {
        return payType;
    }

    public void setPayType(Byte payType) {
        this.payType = payType;
    }

    public String getPayAccount() {
        return payAccount;
    }

    public void setPayAccount(String payAccount) {
        this.payAccount = payAccount == null ? null : payAccount.trim();
    }

    public String getPayCode() {
        return payCode;
    }

    public void setPayCode(String payCode) {
        this.payCode = payCode == null ? null : payCode.trim();
    }

    public BigDecimal getCash() {
        return cash;
    }

    public void setCash(BigDecimal cash) {
        this.cash = cash;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public BigDecimal getNowBalance() {
        return nowBalance;
    }

    public void setNowBalance(BigDecimal nowBalance) {
        this.nowBalance = nowBalance;
    }
}