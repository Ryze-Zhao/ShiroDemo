package com.zhaolearn.shirointegration3.controller;

import com.zhaolearn.shirointegration3.domain.User;
import com.zhaolearn.shirointegration3.service.ShiroService;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/demo")
public class UserController {
    private final static org.slf4j.Logger log = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private ShiroService shiroService;
    //RESTful规范
    @GetMapping(value = "/index")//首页
    public String toIdex() {
        return "/index";
    }
    @GetMapping(value = "/tologin")//登录页
    public String toLogin() {
        return "/tologin";
    }
    @GetMapping(value = "/userPage")//登录后页面
    public String userPage() {
        return "/UserPage";
    }
    @GetMapping(value = "/add")//增加页
    @RequiresPermissions(logical = Logical.OR, value ={"perm1", "perm3"})
    public String add() {
        return "/user/add";
    }
    @GetMapping(value = "/update")//修改页
    @RequiresRoles(logical = Logical.OR, value ={"admin", "test"})
    public String update() {
        return "/user/update";
    }
    @PostMapping(value = "/login")//登录验证
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
}
