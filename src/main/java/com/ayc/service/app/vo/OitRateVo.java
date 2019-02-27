package com.ayc.service.app.vo;

import java.util.Date;

public class OitRateVo {
    private Integer id;

    private String cash;

    private String num;

    private Date createdAt;
    /**
     * 汇率
     */
    private Double huiLv;

    public Double getHuiLv() {
        return huiLv;
    }

    public void setHuiLv(Double huiLv) {
        this.huiLv = huiLv;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCash() {
        return cash;
    }

    public void setCash(String cash) {
        this.cash = cash;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
