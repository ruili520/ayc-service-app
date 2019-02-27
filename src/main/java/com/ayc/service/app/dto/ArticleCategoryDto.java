package com.ayc.service.app.dto;

/**
 * Author:  wwb
 * Date:  2019/1/3011:41
 * Description:
 */
public class ArticleCategoryDto {

    private Integer id;

    private Integer pid;

    private String cateName;

    private Short displayOrder;

    private String pic;

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
        this.cateName = cateName;
    }

    public Short getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(Short displayOrder) {
        this.displayOrder = displayOrder;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }
}
