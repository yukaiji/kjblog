package com.yukaiji.kjblog.model;

import java.io.Serializable;

/**
 * 文章详细内容
 * @author kaijiyu
 */
public class ArticleDetail implements Serializable {
    private Integer id;

    private Integer articleId;

    private String articleImport;

    private String articlePrevTitle;

    private Integer articlePrevId;

    private String articleNextTitle;

    private Integer articleNextId;

    private String articleDetail;

    private String articleMd;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getArticleId() {
        return articleId;
    }

    public void setArticleId(Integer articleId) {
        this.articleId = articleId;
    }

    public String getArticleImport() {
        return articleImport;
    }

    public void setArticleImport(String articleImport) {
        this.articleImport = articleImport == null ? null : articleImport.trim();
    }

    public String getArticlePrevTitle() {
        return articlePrevTitle;
    }

    public void setArticlePrevTitle(String articlePrevTitle) {
        this.articlePrevTitle = articlePrevTitle == null ? null : articlePrevTitle.trim();
    }

    public Integer getArticlePrevId() {
        return articlePrevId;
    }

    public void setArticlePrevId(Integer articlePrevId) {
        this.articlePrevId = articlePrevId;
    }

    public String getArticleNextTitle() {
        return articleNextTitle;
    }

    public void setArticleNextTitle(String articleNextTitle) {
        this.articleNextTitle = articleNextTitle == null ? null : articleNextTitle.trim();
    }

    public Integer getArticleNextId() {
        return articleNextId;
    }

    public void setArticleNextId(Integer articleNextId) {
        this.articleNextId = articleNextId;
    }

    public String getArticleDetail() {
        return articleDetail;
    }

    public void setArticleDetail(String articleDetail) {
        this.articleDetail = articleDetail == null ? null : articleDetail.trim();
    }

    public String getArticleMd() {
        return articleMd;
    }

    public void setArticleMd(String articleMd) {
        this.articleMd = articleMd;
    }
}