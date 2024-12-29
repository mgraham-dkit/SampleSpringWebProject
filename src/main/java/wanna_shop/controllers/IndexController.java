package wanna_shop.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    @GetMapping("/")
    public String getHome(){
        return "index";
    }

    @GetMapping("/registerPage")
    public String registration(){
        return "registration";
    }
}
