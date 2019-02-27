package com.ayc.service.app.vo;

import java.math.BigDecimal;

/**
 * 用户Oit总资产
 */
public class OitTotalVo {
    /**
     * 冻结的oit数
     */
    private BigDecimal oitNum;
    /**
     * oit总额
     */
    private BigDecimal oitTotal;
    /**
     * oit总额转化chy
     */
    private Double oitChy;
    /**
     * 用户余额
     */
    private BigDecimal balance;
    /**
     * 已释放oit数
     */
    private BigDecimal useOitNum;
    /**
     * 已释放oit数转化chy
     */
    private Double useOitChy;

    public BigDecimal getOitNum() {
        return oitNum;
    }

    public void setOitNum(BigDecimal oitNum) {
        this.oitNum = oitNum;
    }

    public BigDecimal getOitTotal() {
        return oitTotal;
    }

    public void setOitTotal(BigDecimal oitTotal) {
        this.oitTotal = oitTotal;
    }

    public Double getOitChy() {
        return oitChy;
    }

    public void setOitChy(Double oitChy) {
        this.oitChy = oitChy;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public BigDecimal getUseOitNum() {
        return useOitNum;
    }

    public void setUseOitNum(BigDecimal useOitNum) {
        this.useOitNum = useOitNum;
    }

    public Double getUseOitChy() {
        return useOitChy;
    }

    public void setUseOitChy(Double useOitChy) {
        this.useOitChy = useOitChy;
    }
}
