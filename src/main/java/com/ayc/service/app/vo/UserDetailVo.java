package com.ayc.service.app.vo;

import java.math.BigDecimal;
import java.util.Date;

public class UserDetailVo {
    private Integer id;

    private Integer uid;

    private Byte type;

    private String nickname;

    private Integer awardTimes;

    private BigDecimal awardCash;

    private Integer buyTimes;

    private BigDecimal buyCash;

    private Byte status;

    private Integer articleNum;

    private Integer awardedTimes;

    private BigDecimal awardedCash;

    private Integer buyedTimes;

    private BigDecimal buyedCash;

    private Date createdAt;

    private Date updatedAt;

    private BigDecimal oitNum;

    private BigDecimal balance;

    private BigDecimal exchangeOitNum;

    private BigDecimal exchangeOitCash;

    private BigDecimal useOitNum;

    private Integer focusNum;

    private Date checkTime;

    private String penName;

    private Byte walletIsRed;
    /**
     * OIT转化成CNY
     */
    private Double chy;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Integer getAwardTimes() {
        return awardTimes;
    }

    public void setAwardTimes(Integer awardTimes) {
        this.awardTimes = awardTimes;
    }

    public BigDecimal getAwardCash() {
        return awardCash;
    }

    public void setAwardCash(BigDecimal awardCash) {
        this.awardCash = awardCash;
    }

    public Integer getBuyTimes() {
        return buyTimes;
    }

    public void setBuyTimes(Integer buyTimes) {
        this.buyTimes = buyTimes;
    }

    public BigDecimal getBuyCash() {
        return buyCash;
    }

    public void setBuyCash(BigDecimal buyCash) {
        this.buyCash = buyCash;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Integer getArticleNum() {
        return articleNum;
    }

    public void setArticleNum(Integer articleNum) {
        this.articleNum = articleNum;
    }

    public Integer getAwardedTimes() {
        return awardedTimes;
    }

    public void setAwardedTimes(Integer awardedTimes) {
        this.awardedTimes = awardedTimes;
    }

    public BigDecimal getAwardedCash() {
        return awardedCash;
    }

    public void setAwardedCash(BigDecimal awardedCash) {
        this.awardedCash = awardedCash;
    }

    public Integer getBuyedTimes() {
        return buyedTimes;
    }

    public void setBuyedTimes(Integer buyedTimes) {
        this.buyedTimes = buyedTimes;
    }

    public BigDecimal getBuyedCash() {
        return buyedCash;
    }

    public void setBuyedCash(BigDecimal buyedCash) {
        this.buyedCash = buyedCash;
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

    public BigDecimal getOitNum() {
        return oitNum;
    }

    public void setOitNum(BigDecimal oitNum) {
        this.oitNum = oitNum;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public BigDecimal getExchangeOitNum() {
        return exchangeOitNum;
    }

    public void setExchangeOitNum(BigDecimal exchangeOitNum) {
        this.exchangeOitNum = exchangeOitNum;
    }

    public BigDecimal getExchangeOitCash() {
        return exchangeOitCash;
    }

    public void setExchangeOitCash(BigDecimal exchangeOitCash) {
        this.exchangeOitCash = exchangeOitCash;
    }

    public BigDecimal getUseOitNum() {
        return useOitNum;
    }

    public void setUseOitNum(BigDecimal useOitNum) {
        this.useOitNum = useOitNum;
    }

    public Integer getFocusNum() {
        return focusNum;
    }

    public void setFocusNum(Integer focusNum) {
        this.focusNum = focusNum;
    }

    public Date getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(Date checkTime) {
        this.checkTime = checkTime;
    }

    public String getPenName() {
        return penName;
    }

    public void setPenName(String penName) {
        this.penName = penName;
    }

    public Byte getWalletIsRed() {
        return walletIsRed;
    }

    public void setWalletIsRed(Byte walletIsRed) {
        this.walletIsRed = walletIsRed;
    }

    public Double getChy() {
        return chy;
    }

    public void setChy(Double chy) {
        this.chy = chy;
    }
}
