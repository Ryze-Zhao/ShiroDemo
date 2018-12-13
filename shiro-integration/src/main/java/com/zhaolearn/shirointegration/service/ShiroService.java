package com.zhaolearn.shirointegration.service;

import com.zhaolearn.shirointegration.domain.User;

public interface ShiroService {
    void loginCheck(User user) throws Exception;

    User findByUserName(String username);

    String findPermByUserName(String userName);
}
