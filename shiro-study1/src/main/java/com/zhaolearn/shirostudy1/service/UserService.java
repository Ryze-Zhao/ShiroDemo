package com.zhaolearn.shirostudy1.service;

import com.zhaolearn.shirostudy1.domain.User;

public interface UserService {
    public User findByUserName(String userName);
}
