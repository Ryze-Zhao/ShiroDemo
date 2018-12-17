package com.zhaolearn.shirojwt.controller;

import com.zhaolearn.shirojwt.domain.ResponseBean;
import com.zhaolearn.shirojwt.domain.User;
import com.zhaolearn.shirojwt.exception.UnauthorizedException;
import com.zhaolearn.shirojwt.service.ShiroService;
import com.zhaolearn.shirojwt.utils.JWTUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public class WebController {

    private static final Logger LOGGER = LogManager.getLogger(WebController.class);

    @Autowired
    private ShiroService shiroService;

    @PostMapping("/login")
    public ResponseBean login(User user) {
        User sqlUser = shiroService.findByUserName(user.getUserName());
        if(sqlUser==null){
            throw new UnauthorizedException("不存在用户");
        }else if(!sqlUser.getPassWord().equals(user.getPassWord())){
            throw new UnauthorizedException("账户或密码不正确！！！");
        }else if (sqlUser.getPassWord().equals(user.getPassWord())) {
            return new ResponseBean(200, "Login success", JWTUtil.sign(user.getUserName(), user.getPassWord()));
        } else if(false){
            throw new UnauthorizedException("预留给封禁的账户");
        } else {
            throw new UnauthorizedException("未知错误");
        }
    }

    @GetMapping("/article")
    public ResponseBean article() {
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated()) {
            return new ResponseBean(200, "You are already logged in", null);
        } else {
            return new ResponseBean(200, "You are guest", null);
        }
    }

    @GetMapping("/require_auth")
    @RequiresAuthentication
    public ResponseBean requireAuth() {
        return new ResponseBean(200, "You are authenticated", null);
    }

    @GetMapping("/require_role")
    @RequiresRoles("admin")
    public ResponseBean requireRole() {
        return new ResponseBean(200, "You are visiting require_role", null);
    }

    @GetMapping("/require_permission")
    @RequiresPermissions(logical = Logical.AND, value = {"view", "edit"})
    public ResponseBean requirePermission() {
        return new ResponseBean(200, "You are visiting permission require edit,view", null);
    }

    @RequestMapping(path = "/401")
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseBean unauthorized() {
        return new ResponseBean(401, "Unauthorized", null);
    }
}
