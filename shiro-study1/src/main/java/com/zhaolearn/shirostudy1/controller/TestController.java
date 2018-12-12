package com.zhaolearn.shirostudy1.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.springframework.data.redis.connection.ReactiveSetCommands;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
    @RequestMapping("/loginUser")
    public String loginUser(String username,String password,Model model) {
        Subject subject=SecurityUtils.getSubject();//1、获取subject
        UsernamePasswordToken token=new UsernamePasswordToken(username,password);//封装用户参数
        try {
            subject.login(token);//3、执行登录方法，如果没异常就是登录成功
            return "redirect:/study1/test";
        } catch (UnknownAccountException uae) {
            //账户不存在
            model.addAttribute("msg","账户不存在");
            return "login";
        } catch (IncorrectCredentialsException ice) {
           //密码不正确
            model.addAttribute("msg","密码不正确");
            return "login";
        } catch (LockedAccountException lae) {
           //用户被锁定了
            model.addAttribute("msg","用户被锁定了");
            return "login";
        } catch (AuthenticationException ae) {
            //无法判断是什么错
            model.addAttribute("msg","无法判断是什么错");
            return "login";
        }
    }
}
