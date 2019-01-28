package com.yukaiji.kjblog.model.responsemodel;

import com.yukaiji.kjblog.model.Article;

import java.util.List;

/**
 * 文章基本信息类
 * @author kaijiyu
 */
public class ArticleResponse extends BasePageResponse {

    private List<Article> articleList;

    public List<Article> getArticleList() {
        return articleList;
    }

    public void setArticleList(List<Article> articleList) {
        this.articleList = articleList;
    }
}