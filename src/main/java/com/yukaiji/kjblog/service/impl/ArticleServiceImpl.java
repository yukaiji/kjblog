package com.yukaiji.kjblog.service.impl;

import com.yukaiji.kjblog.dao.mapper.ArticleDetailMapper;
import com.yukaiji.kjblog.dao.mapper.ArticleMapper;
import com.yukaiji.kjblog.model.Article;
import com.yukaiji.kjblog.model.ArticleDetail;
import com.yukaiji.kjblog.model.responsemodel.BaseResponse;
import com.yukaiji.kjblog.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("articleService")
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private ArticleDetailMapper articleDetailMapper;

    @Override
    @Transactional
    public BaseResponse addArticle(String articleTitle, String articleClass, String articleSubtitle, String articleDigest, String articleDetailBody) {
        BaseResponse baseResponse = new BaseResponse();
        try {
            Article article = new Article();
            article.setArticleDigest(articleDigest);
            article.setArticleClassId(Integer.valueOf(articleClass));
            article.setArticleSubTitle(articleSubtitle);
            article.setArticleTitle(articleTitle);
            int articleId = articleMapper.insert(article);
            if(articleId <= 0) {
                baseResponse.setErrorMessage("系统异常", "9999");
                return baseResponse;
            }
            Article prev = articleMapper.selectPrevArticle(article.getId());
            ArticleDetail articleDetail = new ArticleDetail();
            articleDetail.setArticleId(article.getId());
            articleDetail.setArticleDetail(articleDetailBody);
            articleDetail.setArticlePrevId(prev.getId());
            articleDetail.setArticlePrevTitle(prev.getArticleSubTitle());
            articleDetailMapper.insert(articleDetail);

            ArticleDetail articlePrevDetail = new ArticleDetail();
            articlePrevDetail.setArticleId(prev.getId());
            articlePrevDetail.setArticleNextId(article.getId());
            articlePrevDetail.setArticleNextTitle(article.getArticleSubTitle());
            articleDetailMapper.updateByPrimaryKeySelective(articlePrevDetail);

        } catch (Exception e) {
            baseResponse.setErrorMessage("系统异常", "9999");
        }
        return baseResponse;
    }
}
