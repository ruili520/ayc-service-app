package com.ayc.service.app.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * Author:  wwb
 * Date:  2019/1/30 11:22
 * Description:文章分类实体类
 */
public class ArticleCategoryEntity implements Serializable {

    private static final long serialVersionUID = 1092689242282111410L;

    private Integer id;

    private Integer pid;

    private String cateName;

    private Integer articles;

    private Short displayOrder;

    private Date createdAt;

    private Date updatedAt;

    private String pic;

    public ArticleCategoryEntity() {
    }

    public ArticleCategoryEntity(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getCateName() {
        return cateName;
    }

    public void setCateName(String cateName) {
        this.cateName = cateName == null ? null : cateName.trim();
    }

    public Integer getArticles() {
        return articles;
    }

    public void setArticles(Integer articles) {
        this.articles = articles;
    }

    public Short getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(Short displayOrder) {
        this.displayOrder = displayOrder;
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

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic == null ? null : pic.trim();
    }
}