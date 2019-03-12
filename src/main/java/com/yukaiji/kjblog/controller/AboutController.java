package com.yukaiji.kjblog.controller;

import com.yukaiji.kjblog.dao.mapper.ArticleClassMapper;
import com.yukaiji.kjblog.dao.mapper.ArticleDetailMapper;
import com.yukaiji.kjblog.dao.mapper.ArticleMapper;
import com.yukaiji.kjblog.model.Article;
import com.yukaiji.kjblog.model.ArticleClass;
import com.yukaiji.kjblog.model.ArticleDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * about
 * @author kaijiyu
 */
@Controller
public class AboutController {

    @Autowired
    private ArticleClassMapper articleClassMapper;
    @Autowired
    private ArticleDetailMapper articleDetailMapper;
    @Autowired
    private ArticleMapper articleMapper;

    @RequestMapping("/about")
    public String about(){
        return "about";
    }


    @RequestMapping("/writeArticle")
    public String writeArticle(ModelMap model){
        List<ArticleClass> articleClassList = articleClassMapper.selectByParam(null);
        model.addAttribute("articleClassList", articleClassList);
        return "write";
    }

    @RequestMapping("updateArticle")
    public String updateArticle(ModelMap model, String id){
        if (StringUtils.isEmpty(id)) {
            return "404";
        }
        Article article = articleMapper.selectByPrimaryKey(Integer.valueOf(id));
        ArticleDetail detail = articleDetailMapper.selectByArticleId(Integer.valueOf(id));
        List<ArticleClass> articleClassList = articleClassMapper.selectByParam(null);
        model.addAttribute("articleClassList", articleClassList);
        model.addAttribute("article", detail.getArticleDetail());
        model.addAttribute("id", id);
        model.addAttribute("articleTitle", article.getArticleTitle());
        model.addAttribute("articleClass", article.getArticleClassId());
        model.addAttribute("articleSubtitle", article.getArticleSubTitle());
        model.addAttribute("articleDigest", article.getArticleDigest());
        model.addAttribute("articleMd", detail.getArticleMd());
        return "update";
    }
}
