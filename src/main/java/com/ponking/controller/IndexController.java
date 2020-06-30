package com.ponking.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Peng
 * @date 2020/6/30--17:28
 **/
@Controller
@RequestMapping("/api/admin")
public class IndexController {

    @RequestMapping("index")
    public String index(){
        return "index";
    }
}
