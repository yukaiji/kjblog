package com.yukaiji.kjblog.service;

import com.yukaiji.kjblog.model.responsemodel.BaseResponse;

/**
 * @author kaijiyu
 */
public interface ArticleService {

    /**
     * 添加文章
     * @param articleTitle
     * @param articleClass
     * @param articleSubtitle
     * @param articleDigest
     * @param articleDetail
     * @return
     */
    public BaseResponse addArticle(String articleTitle, String articleClass, String articleSubtitle, String articleDigest, String articleDetail, String articleMd);

    /**
     * 修改文章
     * @param articleTitle
     * @param articleClass
     * @param articleSubtitle
     * @param articleDigest
     * @param articleDetail
     * @param articleMd
     * @param articleId
     * @return
     */
    public BaseResponse updateArticle(String articleTitle, String articleClass, String articleSubtitle,
                                      String articleDigest, String articleDetail, String articleMd, Integer articleId);

}
