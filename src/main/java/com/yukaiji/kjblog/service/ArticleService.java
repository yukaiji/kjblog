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
    public BaseResponse addArticle(String articleTitle, String articleClass, String articleSubtitle, String articleDigest, String articleDetail);

}
