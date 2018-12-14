package com.zhaolearn.shirointegration.service.impl;

import com.zhaolearn.shirointegration.domain.User;
import com.zhaolearn.shirointegration.repository.PermissionRepository;
import com.zhaolearn.shirointegration.repository.UserRepository;
import com.zhaolearn.shirointegration.service.ShiroService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ShiroServiceImpl implements ShiroService {
    @Autowired
    private UserRepository userRepository;
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
    public void loginCheck(User user) throws Exception {
        Subject subject = SecurityUtils.getSubject();//获取当前操作系统的用户
        UsernamePasswordToken token = new UsernamePasswordToken(user.getUserName(), user.getPassWord());//封装用户参数
        token.setRememberMe(true);//是否记住用户，是true，否false（rememberMe只能记住你登录过，但不会记住你是谁以及你的权限信息。）
        try {
            subject.login(token);//执行登录方法，如果没异常就是登录成功
        } catch (UnknownAccountException uae) {
            //账户不存在
            throw new Exception("账户不存在");
        } catch (IncorrectCredentialsException ice) {
            //密码不正确
            throw new Exception("密码不正确");
        } catch (LockedAccountException lae) {
            //用户被锁定了
            throw new Exception("用户被锁定了 ");
        } catch (AuthenticationException ae) {
            //无法判断是什么错
            throw new Exception("未知错误");
        }
    }
}
