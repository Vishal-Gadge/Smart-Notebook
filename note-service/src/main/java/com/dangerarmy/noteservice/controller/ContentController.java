package com.dangerarmy.noteservice.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ContentController {

    @GetMapping("/homepage")
    public String showHomepage(){
        return "index.html";
    }
}
