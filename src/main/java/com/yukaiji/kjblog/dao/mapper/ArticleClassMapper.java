package com.yukaiji.kjblog.dao.mapper;

import com.yukaiji.kjblog.model.ArticleClass;

/**
 * 文章分类数据库操作
 * @author kaijiyu
 */
public interface ArticleClassMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ArticleClass record);

    int insertSelective(ArticleClass record);

    ArticleClass selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ArticleClass record);

    int updateByPrimaryKey(ArticleClass record);
}