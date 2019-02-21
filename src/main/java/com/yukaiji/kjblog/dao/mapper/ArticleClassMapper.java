package com.yukaiji.kjblog.dao.mapper;

import com.yukaiji.kjblog.model.ArticleClass;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 文章分类数据库操作
 * @author kaijiyu
 */
@Component
public interface ArticleClassMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ArticleClass record);

    int insertSelective(ArticleClass record);

    ArticleClass selectByPrimaryKey(Integer id);

    List<ArticleClass> selectByParam(ArticleClass articleClass);

    int updateByPrimaryKeySelective(ArticleClass record);

    int updateByPrimaryKey(ArticleClass record);
}