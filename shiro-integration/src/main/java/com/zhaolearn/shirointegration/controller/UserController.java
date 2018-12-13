package com.zhaolearn.shirointegration.controller;

import com.zhaolearn.shirointegration.domain.User;
import com.zhaolearn.shirointegration.service.ShiroService;
import com.zhaolearn.shirointegration.service.UserService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.logging.Logger;

@Controller
@RequestMapping(value = "/demo")
public class UserController {
    private final static org.slf4j.Logger log = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserService userService;
    @Autowired
    private ShiroService shiroService;
    //RESTful规范
    @GetMapping(value = "/index")
    public String toIdex() {
        return "/index";
    }

    @GetMapping(value = "/tologin")
    public String toLogin() {
        return "/tologin";
    }

    @PostMapping(value = "/login")
    public String login(User user, Model model) {
        log.info("-------------------" + user.toString());
        try {
            shiroService.loginCheck(user);
        } catch (Exception e) {
            model.addAttribute("msg", e.getMessage());
            return "/tologin";
        }
        return "redirect:/demo/userPage";
    }

    @GetMapping(value = "/userPage")
    public String userPage() {
        return "/UserPage";
    }



    @GetMapping(value = "/add")
    public String add() {
        return "/user/add";
    }

    @GetMapping(value = "/update")
    public String update() {
        return "/user/update";
    }








    @GetMapping(value = "/users")
    public String listUser(Model model) {
        model.addAttribute("users", userService.findAll());
        return "/user/listUser";
    }
}
