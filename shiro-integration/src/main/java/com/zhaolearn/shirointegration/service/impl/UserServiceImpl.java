package com.zhaolearn.shirointegration.service.impl;

import com.zhaolearn.shirointegration.domain.User;
import com.zhaolearn.shirointegration.repository.UserRepository;
import com.zhaolearn.shirointegration.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }
}
