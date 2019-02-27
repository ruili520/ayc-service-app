package com.ayc.service.app.dto;

/**
 * 打赏传输类
 *
 * @author wwq
 * @version 1.0
 * @createDate 2019/2/18 15:43
 */
public class ArticleAwardDto {

    /**
     * 打赏用户id
     */
    private Integer uid;

    /**
     * 被打赏用户id
     */
    private Integer toUid;

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

    public Integer getToUid() {
        return toUid;
    }

    public void setToUid(Integer toUid) {
        this.toUid = toUid;
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
