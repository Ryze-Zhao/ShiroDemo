package com.zhaolearn.shirointegration.service.impl;

import com.zhaolearn.shirointegration.repository.UserRepository;
import com.zhaolearn.shirointegration.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
}
