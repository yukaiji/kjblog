package com.yukaiji.kjblog.model.requestmodel;

import java.util.Date;

/**
 * 文章基本信息类
 * @author kaijiyu
 */
public class ArticleRequest extends BasePageRequestModel {
    /** 主键 **/
    private Integer id;
    /** 文章分类id **/
    private Integer articleClassId;
    /** 文章标题 **/
    private String articleTitle;
    /** 文章副标题 **/
    private String articleSubTitle;
    /** 创建时间 **/
    private Date createdTime;
    /** 文章摘要 **/
    private String articleDigest;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getArticleClassId() {
        return articleClassId;
    }

    public void setArticleClassId(Integer articleClassId) {
        this.articleClassId = articleClassId;
    }

    public String getArticleTitle() {
        return articleTitle;
    }

    public void setArticleTitle(String articleTitle) {
        this.articleTitle = articleTitle == null ? null : articleTitle.trim();
    }

    public String getArticleSubTitle() {
        return articleSubTitle;
    }

    public void setArticleSubTitle(String articleSubTitle) {
        this.articleSubTitle = articleSubTitle == null ? null : articleSubTitle.trim();
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public String getArticleDigest() {
        return articleDigest;
    }

    public void setArticleDigest(String articleDigest) {
        this.articleDigest = articleDigest == null ? null : articleDigest.trim();
    }
}