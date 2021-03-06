package com.zhaolearn.shirointegration3.service;

import com.zhaolearn.shirointegration3.domain.User;
import com.zhaolearn.shirointegration3.domain.Role;

import java.util.Set;

public interface ShiroService {
    void loginCheck(User user) throws Exception;

    User findByUserName(String username);

    String findPermByUserName(String userName);

    Role findRoleByUserName(String userName);

    Set<String> findRolesByUserName(String userName);

    Set<String> findPermsByUserName(String userName);
}
