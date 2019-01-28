package com.yukaiji.kjblog.controller;

import com.yukaiji.kjblog.model.responsemodel.BaseResponse;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author kaijiyu
 */
@RestController
public class WriteArticleController extends BaseController{

    @RequestMapping("/write")
    public void writeArticle(HttpServletRequest request, HttpServletResponse response){
        BaseResponse baseResponse = new BaseResponse();
        String article = request.getParameter("article");
        String articleTitle = request.getParameter("articleTitle");


        printJson(response, baseResponse);
    }

}
