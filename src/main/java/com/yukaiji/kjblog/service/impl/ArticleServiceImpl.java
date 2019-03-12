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
    public BaseResponse addArticle(String articleTitle, String articleClass, String articleSubtitle, String articleDigest, String articleDetailBody, String articleMd) {
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
            articleDetail.setArticleMd(articleMd);
            if (prev != null) {
                articleDetail.setArticlePrevId(prev.getId());
                articleDetail.setArticlePrevTitle(prev.getArticleSubTitle());

                ArticleDetail articlePrevDetail = new ArticleDetail();
                articlePrevDetail.setArticleId(prev.getId());
                articlePrevDetail.setArticleNextId(article.getId());
                articlePrevDetail.setArticleNextTitle(article.getArticleSubTitle());
                articleDetailMapper.updateByPrimaryKeySelective(articlePrevDetail);
            }
            articleDetailMapper.insert(articleDetail);

        } catch (Exception e) {
            baseResponse.setErrorMessage("系统异常", "9999");
        }
        return baseResponse;
    }

    @Override
    @Transactional
    public BaseResponse updateArticle(String articleTitle, String articleClass, String articleSubtitle, String articleDigest,
                                      String articleDetail, String articleMd, Integer articleId) {
        BaseResponse baseResponse = new BaseResponse();
        try {
//            Article article = new Article();
//            article.setArticleDigest(articleDigest);
//            article.setArticleClassId(Integer.valueOf(articleClass));
//            article.setArticleSubTitle(articleSubtitle);
//            article.setArticleTitle(articleTitle);
//            article.setId(articleId);
//            articleMapper.updateByPrimaryKeySelective(article);

            ArticleDetail articlePrevDetail = new ArticleDetail();
            articlePrevDetail.setArticleId(articleId);
            articlePrevDetail.setArticleMd(articleMd);
            articlePrevDetail.setArticleDetail(articleDetail);
            articleDetailMapper.updateByPrimaryKeySelective(articlePrevDetail);
        } catch (Exception e) {
            baseResponse.setErrorMessage("系统异常", "9999");
        }
        return baseResponse;
    }
}
