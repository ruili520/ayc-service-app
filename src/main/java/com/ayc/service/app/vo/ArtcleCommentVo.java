package com.ayc.service.app.vo;

import com.ayc.service.uc.entity.AycUserEntity;

import java.util.Date;

public class ArtcleCommentVo {
    private Integer id;

    private Integer uid;

    private Integer articleId;

    private String desc;

    private Date createdAt;

    private AycUserEntity aycUserEntity;

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

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

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public AycUserEntity getAycUserEntity() {
        return aycUserEntity;
    }

    public void setAycUserEntity(AycUserEntity aycUserEntity) {
        this.aycUserEntity = aycUserEntity;
    }
}
