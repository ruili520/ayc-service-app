package com.ayc.service.app.vo;

/**
 * 文章分享视图对象
 *
 * @author wwq
 * @version 1.0
 * @createDate 2019/2/25 14:05
 */
public class ArticleShareVo {

    /**
     * 标题
     */
    private String title;

    /**
     * 内容地址
     */
    private String url;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
