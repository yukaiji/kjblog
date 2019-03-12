package com.yukaiji.kjblog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 登录接口，登录后可添加文章，上传图片
 * @author kaijiyu
 */
@Controller
public class LoginController extends BaseController {

    @RequestMapping("/login")
    public String login(){
        return "login";
    }


    @RequestMapping("/userLogin")
    public void login(HttpServletRequest request, HttpServletResponse response) {

    }



}
