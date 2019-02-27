package com.ayc.service.app.dto;

/**
 * 文章收藏、点赞传输类
 * @author wwq
 * @version 1.0
 * @createDate 2019/2/11 15:01
 */
public class ArticleTimesDto {

    /**
     * 文章id
     */
    private Integer articleId;

    /**
     * 写文章的用户id
     */
    private Integer articleUid;

    /**
     * 当前用户id
     */
    private Integer uid;

    /**
     * 状态类型 ArticleTimeEnums
     */
    private Byte type;


    public Integer getArticleId() {
        return articleId;
    }

    public void setArticleId(Integer articleId) {
        this.articleId = articleId;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public Integer getArticleUid() {
        return articleUid;
    }

    public void setArticleUid(Integer articleUid) {
        this.articleUid = articleUid;
    }
}
