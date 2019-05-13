package com.yukaiji.kjblog.controller;

import com.yukaiji.kjblog.dao.mapper.ArticleDetailMapper;
import com.yukaiji.kjblog.dao.mapper.ArticleMapper;
import com.yukaiji.kjblog.model.Article;
import com.yukaiji.kjblog.model.ArticleDetail;
import com.yukaiji.kjblog.model.requestmodel.ArticleRequest;
import com.yukaiji.kjblog.model.responsemodel.ArticleResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author kaijiyu
 */
@Controller
public class ArticleController {


    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private ArticleDetailMapper articleDetailMapper;


    @RequestMapping("/index")
    public String index(ModelMap model, Integer pageSize){
        ArticleRequest articleParam = new ArticleRequest();
        if (null == pageSize || pageSize == 0) {
            pageSize = 1;
        }
        articleParam.setPageSize(pageSize);
        articleParam.setStartNum((pageSize-1) * 10);
        articleParam.setEndNum(10);
        List<Article> articleList = articleMapper.selectByParam(articleParam);
        int count = articleMapper.selectCount();

        ArticleResponse articleResponse = new ArticleResponse();
        articleResponse.setArticleList(articleList);

        int size = count / 10;
        int sizeLess = count % 10;
        if (count < 10){
            size = 1;
        }else if (sizeLess > 0){
            size++;
        }

        articleResponse.setTotalCount(count);
        articleResponse.setPageSize(size);
        articleResponse.setNextPageNum(size > pageSize + 1? pageSize + 1: size);
        if (size == pageSize){
            articleResponse.setNextPageNum(0);
        }
        articleResponse.setPrevPageNum(pageSize >= 1 ? pageSize - 1 : pageSize);
        model.addAttribute("articleResp", articleResponse);
        return "index";
    }


    @RequestMapping("/detail")
    public String detail(ModelMap model, String articleId){
        if (StringUtils.isEmpty(articleId)) {
            return "error";
        }
        ArticleDetail articleDetail = articleDetailMapper.selectByArticleId(Integer.valueOf(articleId));
        Article article = articleMapper.selectByPrimaryKey(articleDetail.getArticleId());
        model.addAttribute("articleDetail", articleDetail);
        model.addAttribute("article", article);
        return "detail";
    }
}
