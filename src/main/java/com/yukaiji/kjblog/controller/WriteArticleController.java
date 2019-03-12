package com.yukaiji.kjblog.controller;

import com.yukaiji.kjblog.dao.mapper.ArticleMapper;
import com.yukaiji.kjblog.model.Article;
import com.yukaiji.kjblog.model.responsemodel.BaseResponse;
import com.yukaiji.kjblog.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * @author kaijiyu
 */
@RestController
public class WriteArticleController extends BaseController{

    @Autowired
    private ArticleService articleService;

    @RequestMapping("/write")
    public void write(HttpServletRequest request, HttpServletResponse response){
        BaseResponse baseResponse = new BaseResponse();
        String articleDetail = request.getParameter("article");
        String articleTitle = request.getParameter("articleTitle");
        String articleClass = request.getParameter("articleClass");
        String articleSubtitle = request.getParameter("articleSubtitle");
        String articleDigest = request.getParameter("articleDigest");
        String articleMd = request.getParameter("articleMd");
        articleService.addArticle(articleTitle, articleClass, articleSubtitle, articleDigest, articleDetail, articleMd);
        printJson(response, baseResponse);
    }

    @RequestMapping("/update")
    public void update(HttpServletRequest request, HttpServletResponse response){
        BaseResponse baseResponse = new BaseResponse();
        String articleDetail = request.getParameter("article");
        String articleTitle = request.getParameter("articleTitle");
        String articleClass = request.getParameter("articleClass");
        String articleSubtitle = request.getParameter("articleSubtitle");
        String articleDigest = request.getParameter("articleDigest");
        String articleMd = request.getParameter("articleMd");
        String articleId = request.getParameter("articleId");
        articleService.updateArticle(articleTitle, articleClass, articleSubtitle, articleDigest, articleDetail, articleMd, Integer.valueOf(articleId));
        printJson(response, baseResponse);
    }


    @RequestMapping("/uploadfile")
    public void uploadfile(@RequestParam(value = "editormd-image-file", required = true) MultipartFile file,
                           HttpServletRequest request, HttpServletResponse response){
        String trueFileName = file.getOriginalFilename();
        String suffix = trueFileName.substring(trueFileName.lastIndexOf("."));
        String fileName = System.currentTimeMillis()+suffix;
        String path = null;
        Map<String, Object> uploadResp = new HashMap<>();
        try {
            path = ResourceUtils.getURL("classpath:").getPath() + "static/img/upload/";
            System.out.println(path);
            File targetFile = new File(path, fileName);
            if(!targetFile.getParentFile().exists()){
                targetFile.getParentFile().mkdirs();
            }
            file.transferTo(targetFile);
            uploadResp.put("url", "/upload/" + fileName);
            uploadResp.put("message","上传成功");
            uploadResp.put("success",1);
        } catch (Exception e) {
            e.printStackTrace();
            uploadResp.put("message","上传失败");
            uploadResp.put("success",0);
        }
        printJson(response, uploadResp);
    }
}
