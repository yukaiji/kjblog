package com.yukaiji.kjblog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * about
 * @author kaijiyu
 */
@Controller
public class AboutController {

    @RequestMapping("/about")
    public String about(){
        return "about";
    }


    @RequestMapping("/Maniykj0703")
    public String write(){
        return "write";
    }
}
