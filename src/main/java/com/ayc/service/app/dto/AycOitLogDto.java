package com.ayc.service.app.dto;
/**
 * @Title:
 * @Description: //TODO oit列表展示前台向后台传值DTO类
 * @Author: 程亚磊
 * @Date: 2019/2/25 18:31
 */
public class AycOitLogDto {

    /**
     * 用户id
     */
    private Integer uid;

    /**
     * 每页数量
     */
    private Integer pageSize;

    /**
     * 页码
     */
    private Integer pageNum;

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }
}
