package com.ayc.service.app.dto;

/**
 * 文章分享dto
 *
 * @author wwq
 * @version 1.0
 * @createDate 2019/2/25 14:13
 */
public class ArticleShareDto {

    /**
     * 文章id
     */
    private Integer articleId;

    /**
     * 分享目标
     * 取值范围
     */
    private Integer target;

    public Integer getArticleId() {
        return articleId;
    }

    public void setArticleId(Integer articleId) {
        this.articleId = articleId;
    }

    public Integer getTarget() {
        return target;
    }

    public void setTarget(Integer target) {
        this.target = target;
    }
}
