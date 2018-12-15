package com.zhaolearn.shirointegration.service;

import com.zhaolearn.shirointegration.domain.User;
import com.zhaolearn.shirointegration.domain.Role;

public interface ShiroService {
    void loginCheck(User user) throws Exception;

    User findByUserName(String username);

    String findPermByUserName(String userName);

    Role findRoleByUserName(String userName);
}
