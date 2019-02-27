package com.ayc.service.app.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 个人账户流水传输类
 *
 * @author wwq
 * @version 1.0
 * @createDate 2019/2/23 16:35
 */
public class AycFinancialDto implements Serializable {

    private Byte action;

    private Byte payType;

    private String payAccount;

    private String payCode;

    private BigDecimal cash;

    private Integer uid;

    private Date createdAt;

    private Date updatedAt;

    private BigDecimal nowBalance;

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
        this.payAccount = payAccount;
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
