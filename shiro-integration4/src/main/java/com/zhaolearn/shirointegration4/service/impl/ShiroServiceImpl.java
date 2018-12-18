package com.zhaolearn.shirointegration4.service.impl;

import com.zhaolearn.shirointegration4.common.ResultDTO;
import com.zhaolearn.shirointegration4.controller.UserController;
import com.zhaolearn.shirointegration4.domain.Role;
import com.zhaolearn.shirointegration4.domain.User;
import com.zhaolearn.shirointegration4.repository.PermissionRepository;
import com.zhaolearn.shirointegration4.repository.RoleRepository;
import com.zhaolearn.shirointegration4.repository.UserRepository;
import com.zhaolearn.shirointegration4.service.ShiroService;
import com.zhaolearn.shirointegration4.shiro.JwtToken;
import com.zhaolearn.shirointegration4.shiro.JwtUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;


@Service
public class ShiroServiceImpl implements ShiroService {
    private final static org.slf4j.Logger LOGGER = LoggerFactory.getLogger(ShiroServiceImpl.class);
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PermissionRepository permissionRepository;
    @Override
    public User findByUserName(String username) {
        return userRepository.findByUserName(username);
    }
    @Override
    public String findPermByUserName(String userName) {
        return permissionRepository.findPermByUserName(userName);
    }

    @Override
    public Role findRoleByUserName(String userName) {
        return roleRepository.findByUserName(userName);
    }

    @Override
    public Set<String> findRolesByUserName(String userName) {
        return roleRepository.findRolesByUserName(userName);
    }

    @Override
    public Set<String> findPermsByUserName(String userName) {
        return permissionRepository.findPermsByUserName(userName);
    }

    @Override
    public ResultDTO loginCheck(User user){
        LOGGER.info("loginCheck-----------");
        Subject subject = SecurityUtils.getSubject();
        String tokenStrinf=JwtUtil.sign(user.getUserName(), user.getPassWord());
        //封装用户参数
        JwtToken token = new JwtToken(tokenStrinf);
        try {
            //执行登录方法，如果没异常就是登录成功
            subject.login(token);
            return new ResultDTO(200, "登陆成功", "1");
        } catch (UnknownAccountException uae) {
            //账户不存在
            return new ResultDTO(200, "账户不存在", null);
        } catch (IncorrectCredentialsException ice) {
            //密码不正确
            return new ResultDTO(200, "密码不正确", null);
        } catch (LockedAccountException lae) {
            //用户被锁定了
            return new ResultDTO(200, "用户被锁定了", null);
        } catch (AuthenticationException ae) {
            //无法判断是什么错
            return new ResultDTO(200, "未知错误", null);
        }
    }
}
