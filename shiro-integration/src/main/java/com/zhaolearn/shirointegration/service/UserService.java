package com.zhaolearn.shirointegration.service;

import com.zhaolearn.shirointegration.domain.User;

import java.util.List;

public interface UserService {
    List<User> findAll();
}