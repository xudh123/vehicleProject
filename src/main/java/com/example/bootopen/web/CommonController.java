package com.example.bootopen.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CommonController {
    @RequestMapping("/common/header.html")
    public String getHeader(){
        return "common/header";
    }

    @RequestMapping("/common/footer.html")
    public String getFooter(){
        return "common/footer";
    }
}
