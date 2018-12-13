package com.zhaolearn.shirostudy1.service.impl;

import com.zhaolearn.shirostudy1.domain.User;
import com.zhaolearn.shirostudy1.dao.UserMapper;
import com.zhaolearn.shirostudy1.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Override
    public User findByUserName(String userName) {
        return userMapper.findByUserName(userName);
    }
}
