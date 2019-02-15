package com.yukaiji.kjblog.controller;

import com.alibaba.fastjson.JSONObject;
import com.yukaiji.kjblog.model.responsemodel.BaseResponse;
import org.apache.tomcat.util.bcel.classfile.Constant;
import org.springframework.http.HttpRequest;
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

    @RequestMapping("/write")
    public void writeArticle(HttpServletRequest request, HttpServletResponse response){
        BaseResponse baseResponse = new BaseResponse();
        String article = request.getParameter("article");
        String articleTitle = request.getParameter("articleTitle");


        printJson(response, baseResponse);
    }

    @RequestMapping("/uploadfile")
    public void uploadfile(@RequestParam(value = "editormd-image-file", required = true) MultipartFile file,
                           HttpServletRequest request, HttpServletResponse response){
        BaseResponse baseResponse = new BaseResponse();
        String trueFileName = file.getOriginalFilename();

        String suffix = trueFileName.substring(trueFileName.lastIndexOf("."));

        String fileName = System.currentTimeMillis()+suffix;

        String path = request.getSession().getServletContext().getRealPath("/assets/msg/upload/");
        System.out.println(path);

        File targetFile = new File(path, fileName);
        if(!targetFile.exists()){
            targetFile.mkdirs();
        }
        //保存
        try {
            file.transferTo(targetFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
        JSONObject res = new JSONObject();
        res.put("url","");
        res.put("success", 1);
        res.put("message", "upload success!");
        printJson(response, baseResponse);
    }
}
