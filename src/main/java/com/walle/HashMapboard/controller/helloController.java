package com.walle.HashMapboard.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class helloController {

    @GetMapping("/")
    public String hello() {
        return "index";
    }

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String hello(Model model) {
        model.addAttribute("serverName", "Multipart Server!!!");

        return "hello";
    }
}



