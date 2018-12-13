package com.zhaolearn.shirointegration.controller;

import com.zhaolearn.shirointegration.domain.User;
import com.zhaolearn.shirointegration.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping(value = "/demo")
public class UserController {
    @Autowired
    private UserService userService;
    //RESTful规范
    @GetMapping(value = "/users")
    public List<User> listUser() {
        return null;
    }
}
