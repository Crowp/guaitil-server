package com.guaitilsoft.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Controller
public class HomeController {

    @RequestMapping(value = "/")
    public String index(){
        return "index.html";
    }
}
