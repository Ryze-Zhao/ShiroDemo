package com.zhaolearn.shirointegration4.controller;

import com.zhaolearn.shirointegration4.common.ResultDTO;
import com.zhaolearn.shirointegration4.domain.User;
import com.zhaolearn.shirointegration4.service.ShiroService;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
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

    @PostMapping(value = "/login")//登录验证
    public ResultDTO login(User user) {
        LOGGER.info("login-----------");
        //进入这个方法，最后会进入到自定义Realm的AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken auth)方法
        ResultDTO result= shiroService.loginCheck(user);
        return result;
    }
    @RequestMapping(path = "/401")
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResultDTO unauthorized() {
        return new ResultDTO(401, "UserController输出无权限", null);
    }
}
