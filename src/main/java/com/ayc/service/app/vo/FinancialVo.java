package com.ayc.service.app.vo;

import java.math.BigDecimal;
import java.util.Date;

public class FinancialVo {

    private Integer id;//现金明细id号

    private Byte action;//类型

    private String payCode;//流水号

    private BigDecimal cash;//金额

    private Integer uid;//用户id

    private Date createdAt;//时间

    private BigDecimal nowBalance;//当前用户余额

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

    public String getPayCode() {
        return payCode;
    }

    public void setPayCode(String payCode) {
        this.payCode = payCode;
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

    public BigDecimal getNowBalance() {
        return nowBalance;
    }

    public void setNowBalance(BigDecimal nowBalance) {
        this.nowBalance = nowBalance;
    }
}
