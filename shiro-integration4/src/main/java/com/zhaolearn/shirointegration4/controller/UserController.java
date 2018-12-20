package com.zhaolearn.shirointegration4.controller;

import com.zhaolearn.shirointegration4.common.ResultDTO;
import com.zhaolearn.shirointegration4.domain.User;
import com.zhaolearn.shirointegration4.service.ShiroService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {
    private final static org.slf4j.Logger LOGGER = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private ShiroService shiroService;

    @PostMapping("/login")
    public ResultDTO login(User user) {
        String result="";
        try {
            //进入这个方法，最后会进入到自定义Realm的AuthenticationInfo
            // doGetAuthenticationInfo(AuthenticationToken auth)方法
            result= shiroService.loginCheck(user);
        }catch (Exception e) {
            return new ResultDTO(200, e.getMessage(), null);
        }
        return new ResultDTO(200, "登录成功", result);
    }
    @GetMapping("/boolLogin")
    public ResultDTO article() {
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated()) {
            return new ResultDTO(200, "你已经登录", null);
        } else {
            return new ResultDTO(200, "目前还未登录", null);
        }
    }

    @GetMapping("/authentication")
    @RequiresAuthentication
    public ResultDTO requireAuth() {
        return new ResultDTO(200, "验证Token成功", null);
    }
    @GetMapping("/role")
    @RequiresRoles(logical = Logical.OR, value = {"test", "admin"})
    public ResultDTO requireRole() {
        return new ResultDTO(200, "你拥有test或admin角色", null);
    }
    @GetMapping("/perm")
    @RequiresPermissions(logical = Logical.OR, value = {"perm1", "perm3"})
    public ResultDTO requirePermission() {
        return new ResultDTO(200, "你拥有perm1或perm3权限", null);
    }
    @RequestMapping(path = "/401")
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResultDTO unauthorized() {
        return new ResultDTO(401, "你没有权限", null);
    }










}
