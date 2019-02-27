package com.ayc.service.app.vo;

import com.ayc.service.app.entity.ArticleEntity;

import java.util.List;

/**
 * 搜索结果视图对象
 *
 * @author wwq
 * @version 1.0
 * @createDate 2019/2/11 16:19
 */
public class SearchResultVo {

    private List<ArticleEntity> articleList;

    private List aycUser;


    public List<ArticleEntity> getArticleList() {
        return articleList;
    }

    public void setArticleList(List<ArticleEntity> articleList) {
        this.articleList = articleList;
    }

    public List getAycUser() {
        return aycUser;
    }

    public void setAycUser(List aycUser) {
        this.aycUser = aycUser;
    }
}
