package com.ayc.service.app.vo;

import java.util.List;

/**
 * 文章列表中每篇文章的视图对象
 *
 * @author wwq
 * @version 1.0
 * @createDate 2019/2/18 18:35
 */
public class ArticleListVo {

    /**
     * 用户头像
     */
    private String avatar;

    /**
     * 文章id
     */
    private String id;

    /**
     * 文章标题
     */
    private String title;

    /**
     * 文章内容
     */
    private String desc;

    /**
     * 文章封面图
     */
    private String pic;

    /**
     * 评论数
     */
    private Integer commentNum;

    /**
     * 收藏数
     */
    private Integer collectNum;

    /**
     * 点赞数
     */
    private Integer likeNum;

    /**
     * 分享数
     */
    private Integer shareNum;

    private List<ArticleAwardLogVo> logVos;

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public Integer getCommentNum() {
        return commentNum;
    }

    public void setCommentNum(Integer commentNum) {
        this.commentNum = commentNum;
    }

    public Integer getCollectNum() {
        return collectNum;
    }

    public void setCollectNum(Integer collectNum) {
        this.collectNum = collectNum;
    }

    public Integer getLikeNum() {
        return likeNum;
    }

    public void setLikeNum(Integer likeNum) {
        this.likeNum = likeNum;
    }

    public Integer getShareNum() {
        return shareNum;
    }

    public void setShareNum(Integer shareNum) {
        this.shareNum = shareNum;
    }
}
