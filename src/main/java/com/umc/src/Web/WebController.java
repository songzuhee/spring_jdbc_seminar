package com.umc.src.Web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {

    @GetMapping("/home")
    public String homepage() {
        return "home";
    }
}
