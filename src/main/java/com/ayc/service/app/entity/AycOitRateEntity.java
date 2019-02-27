package com.ayc.service.app.entity;

import java.io.Serializable;
import java.util.Date;

public class AycOitRateEntity implements Serializable {

    private static final long serialVersionUID = -628193558111666289L;

    private Integer id;

    private String cash;

    private String num;

    private Date createdAt;

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
        this.cash = cash == null ? null : cash.trim();
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num == null ? null : num.trim();
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}