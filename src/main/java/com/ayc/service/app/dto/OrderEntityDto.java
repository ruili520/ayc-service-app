package com.ayc.service.app.dto;

import java.math.BigDecimal;

/**
 * 订单的传输类
 *
 * @author wwq
 * @version 1.0
 * @createDate 2019/2/13 13:17
 */
public class OrderEntityDto {

    /**
     * 订单支付金额
     */
    private BigDecimal amount;

    /**
     * 订单支付的用户id
     */
    private Integer uid;

    /**
     * 订单类型
     *  com.ayc.common.enums.OrderEnums.Types
     */
    private Byte orderType;

    /**
     * 订单类型对应的id
     */
    private Integer objId;

    /**
     * 打赏功能特有属性：被打赏的用户id
     */
    private Integer toUid;

    /**
     * 文章id
     */
    private Integer articleId;

    /**
     * 文章标题
     */
    private String articleTitle;

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Integer getObjId() {
        return objId;
    }

    public void setObjId(Integer objId) {
        this.objId = objId;
    }

    public Byte getOrderType() {
        return orderType;
    }

    public void setOrderType(Byte orderType) {
        this.orderType = orderType;
    }

    public Integer getArticleId() {
        return articleId;
    }

    public void setArticleId(Integer articleId) {
        this.articleId = articleId;
    }

    public Integer getToUid() {
        return toUid;
    }

    public void setToUid(Integer toUid) {
        this.toUid = toUid;
    }

    public String getArticleTitle() {
        return articleTitle;
    }

    public void setArticleTitle(String articleTitle) {
        this.articleTitle = articleTitle;
    }
}
