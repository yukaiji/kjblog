package com.yukaiji.kjblog.controller;

import com.yukaiji.kjblog.dao.mapper.ArticleClassMapper;
import com.yukaiji.kjblog.model.ArticleClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
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

    @RequestMapping("/about")
    public String about(){
        return "about";
    }


    @RequestMapping("/writeArticle")
    public String write(ModelMap model){
        List<ArticleClass> articleClassList = articleClassMapper.selectByParam(null);
        model.addAttribute("articleClassList", articleClassList);
        return "write";
    }
}
