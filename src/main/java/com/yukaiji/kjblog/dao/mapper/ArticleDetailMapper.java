package com.yukaiji.kjblog.dao.mapper;

import com.yukaiji.kjblog.model.ArticleDetail;
import org.springframework.stereotype.Component;

/**
 * 文章详情数据库操作
 * @author kaijiyu
 */
@Component
public interface ArticleDetailMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ArticleDetail record);

    int insertSelective(ArticleDetail record);

    ArticleDetail selectByPrimaryKey(Integer id);

    ArticleDetail selectByArticleId(Integer id);

    int updateByPrimaryKeySelective(ArticleDetail record);

    int updateByPrimaryKeyWithBLOBs(ArticleDetail record);

    int updateByPrimaryKey(ArticleDetail record);
}