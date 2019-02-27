package com.ayc.service.app.dto;

import java.math.BigDecimal;

/**
 * Author:  wwb
 * Date:  2019/2/1114:09
 * Description:
 */
public class ArticleDto {
    private Integer pageNum;

    private Integer pageSize;

    private Integer id;

    private Integer cateId;

    private String title;

    private Integer uid;

    private String author;

    private Byte from;

    private Byte isFree;

    private BigDecimal cash;

    private Integer catePid;

    private Integer readTimes;

    private BigDecimal price;

    private Byte freePercent;

    private Integer commentNum;

    private Byte status;

    private String penName;

    private String desc;

    private String likeDesc;//模糊搜索标题和作者

    /**
     * 排序字段
     */
    private String orderBy;

    /**
     * 排序顺序
     * 取值范围 SystemEnums.Order
     */
    private String order;

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCateId() {
        return cateId;
    }

    public void setCateId(Integer cateId) {
        this.cateId = cateId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Byte getFrom() {
        return from;
    }

    public void setFrom(Byte from) {
        this.from = from;
    }

    public Byte getIsFree() {
        return isFree;
    }

    public void setIsFree(Byte isFree) {
        this.isFree = isFree;
    }

    public BigDecimal getCash() {
        return cash;
    }

    public void setCash(BigDecimal cash) {
        this.cash = cash;
    }

    public Integer getCatePid() {
        return catePid;
    }

    public void setCatePid(Integer catePid) {
        this.catePid = catePid;
    }

    public Integer getReadTimes() {
        return readTimes;
    }

    public void setReadTimes(Integer readTimes) {
        this.readTimes = readTimes;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Byte getFreePercent() {
        return freePercent;
    }

    public void setFreePercent(Byte freePercent) {
        this.freePercent = freePercent;
    }

    public Integer getCommentNum() {
        return commentNum;
    }

    public void setCommentNum(Integer commentNum) {
        this.commentNum = commentNum;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public String getPenName() {
        return penName;
    }

    public void setPenName(String penName) {
        this.penName = penName;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getLikeDesc() {
        return likeDesc;
    }

    public void setLikeDesc(String likeDesc) {
        this.likeDesc = likeDesc;
    }
}
