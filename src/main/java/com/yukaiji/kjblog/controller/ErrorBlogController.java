package com.yukaiji.kjblog.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * 统一Error返回页面处理
 * @author kaijiyu
 */
@Controller
public class ErrorBlogController implements ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request){
        //获取statusCode:401,404,500
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if(statusCode == 404){
            return "404";
        }else if(statusCode == 500){
            return "500";
        }else{
            return "error";
        }
    }

    @Override
    public String getErrorPath() {
        return "error";
    }
}
