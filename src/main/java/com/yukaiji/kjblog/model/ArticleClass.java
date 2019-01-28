package com.yukaiji.kjblog.model;

import java.io.Serializable;

/**
 * 文章分类
 * @author kaijiyu
 */
public class ArticleClass implements Serializable {
    private Integer id;

    private String articleClassName;

    private Integer articleClassId;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getArticleClassName() {
        return articleClassName;
    }

    public void setArticleClassName(String articleClassName) {
        this.articleClassName = articleClassName == null ? null : articleClassName.trim();
    }

    public Integer getArticleClassId() {
        return articleClassId;
    }

    public void setArticleClassId(Integer articleClassId) {
        this.articleClassId = articleClassId;
    }
}