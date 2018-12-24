package com.zhaolearn.shirointegration5.service.impl;

import com.zhaolearn.shirointegration5.common.jwt.JWTToken;
import com.zhaolearn.shirointegration5.common.jwt.JWTUtil;
import com.zhaolearn.shirointegration5.domain.Role;
import com.zhaolearn.shirointegration5.domain.User;
import com.zhaolearn.shirointegration5.repository.PermissionRepository;
import com.zhaolearn.shirointegration5.repository.RoleRepository;
import com.zhaolearn.shirointegration5.repository.UserRepository;
import com.zhaolearn.shirointegration5.service.ShiroService;
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
    private final static Logger logger = LoggerFactory.getLogger(ShiroServiceImpl.class);
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
    public String loginCheck(User user) throws Exception {
        //获取当前操作系统的用户
        Subject subject = SecurityUtils.getSubject();
        String tokenStrinf=JWTUtil.sign(user.getUserName(), user.getPassWord());
        //封装用户参数
        JWTToken token = new JWTToken(tokenStrinf);
        try {
            //执行登录方法，如果没异常就是登录成功
            subject.login(token);
            return tokenStrinf;
        }catch (UnknownAccountException uae) {
            //账户不存在
            throw new UnknownAccountException("账户不存在");
        } catch (IncorrectCredentialsException ice) {
            //密码不正确
            throw new IncorrectCredentialsException("密码不正确");
        } catch (LockedAccountException lae) {
            //用户被锁定了
            throw new LockedAccountException("用户被锁定了 ");
        } catch (AuthenticationException ae) {
            //无法判断是什么错
            throw new AuthenticationException("未知错误");
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
}
