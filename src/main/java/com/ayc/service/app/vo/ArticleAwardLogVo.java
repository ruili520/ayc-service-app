package com.ayc.service.app.vo;

import com.ayc.service.app.entity.ArticleEntity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 打赏记录视图对象
 *
 * @author wwq
 * @version 1.0
 * @createDate 2019/2/18 15:47
 */
public class ArticleAwardLogVo {
    private Integer id;

    /**
     * 文章id
     */
    private Integer articleId;

    /**
     * 文章标题
     */
    private String articleTitle;

    /**
     * 打赏用户
     */
    private Integer uid;

    /**
     * 被打赏用户
     */
    private Integer toUid;

    /**
     * 打赏 金额
     */
    private BigDecimal cash;

    /**
     * 打赏时间
     */
    private Date createdAt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getArticleId() {
        return articleId;
    }

    public void setArticleId(Integer articleId) {
        this.articleId = articleId;
    }

    public String getArticleTitle() {
        return articleTitle;
    }

    public void setArticleTitle(String articleTitle) {
        this.articleTitle = articleTitle;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Integer getToUid() {
        return toUid;
    }

    public void setToUid(Integer toUid) {
        this.toUid = toUid;
    }

    public BigDecimal getCash() {
        return cash;
    }

    public void setCash(BigDecimal cash) {
        this.cash = cash;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
