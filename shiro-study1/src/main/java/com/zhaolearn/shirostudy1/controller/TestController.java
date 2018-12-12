package com.zhaolearn.shirostudy1.controller;

import org.springframework.data.redis.connection.ReactiveSetCommands;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/study1")
public class TestController {
    @RequestMapping("/test")
    public String test() {
        return "test";
    }
    @RequestMapping("/add")
    public String add() {
        return "/user/add";
    }
    @RequestMapping("/update")
    public String update() {
        return "/user/update";
    }
    @RequestMapping("/login")
    public String login() {
        return "login";
    }
}
