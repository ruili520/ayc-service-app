package com.ayc.service.app.dto;

import java.math.BigDecimal;

/**
 * @Title:
 * @Description: //TODO 提现需要从前台传过来的值
 * @Author: 程亚磊
 * @Date: 2019/2/25 20:58
 */
public class WithdrawDto {

    private BigDecimal cash;//提现金额

    private Integer uid;//关联的用户id

    private BigDecimal nowBalance;//用户当前的余额

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

    public BigDecimal getNowBalance() {
        return nowBalance;
    }

    public void setNowBalance(BigDecimal nowBalance) {
        this.nowBalance = nowBalance;
    }
}
