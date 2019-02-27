package com.ayc.service.app.dto;

/**
 *
 * @author wwq
 * @version 1.0
 * @createDate 2019/2/11 15:01
 */
public class ArticleSearchDto {

    /**
     * 搜索文本
     */
    private String searchText;

    /**
     * 推荐提示条数
     */
    private Integer adviceNum;

    /**
     * 作者id
     */
    private Integer uid;

    /**
     * 页码
     */
    private Integer pageNum;

    /**
     * 每页大小
     */
    private Integer pageSize;

    /**
     * 排序字段
     */
    private String orderBy;

    /**
     * 排序顺序
     * 取值范围 SystemEnums.Order
     */
    private String order;

    public String getSearchText() {
        return searchText;
    }

    public void setSearchText(String searchText) {
        this.searchText = searchText;
    }

    public Integer getAdviceNum() {
        return adviceNum;
    }

    public void setAdviceNum(Integer adviceNum) {
        this.adviceNum = adviceNum;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

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
}
