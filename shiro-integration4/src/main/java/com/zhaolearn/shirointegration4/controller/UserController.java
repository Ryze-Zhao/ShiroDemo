package com.zhaolearn.shirointegration4.controller;

import com.zhaolearn.shirointegration4.domain.User;
import com.zhaolearn.shirointegration4.service.ShiroService;
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
public class UserController {
    private final static org.slf4j.Logger LOGGER = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private ShiroService shiroService;












    @PostMapping(value = "/login")//登录验证
    public String login(User user, Model model) {
        LOGGER.info("-------------------" + user.toString());
        try {
            shiroService.loginCheck(user);
        } catch (Exception e) {
            model.addAttribute("msg", e.getMessage());
            return "/tologin";
        }
        return "redirect:/demo/userPage";
    }
}
