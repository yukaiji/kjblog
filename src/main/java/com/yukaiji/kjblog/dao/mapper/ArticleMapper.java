package com.yukaiji.kjblog.dao.mapper;

import com.yukaiji.kjblog.model.Article;
import com.yukaiji.kjblog.model.requestmodel.ArticleRequest;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 文章基本信息数据库操作
 * @author kaijiyu
 */
@Component
public interface ArticleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Article record);

    int insertSelective(Article record);

    Article selectByPrimaryKey(Integer id);

    List<Article> selectByParam(ArticleRequest articleParam);

    int selectCount();

    int updateByPrimaryKeySelective(Article record);

    int updateByPrimaryKeyWithBLOBs(Article record);

    int updateByPrimaryKey(Article record);
}