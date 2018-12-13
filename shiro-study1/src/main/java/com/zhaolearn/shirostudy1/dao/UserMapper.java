package com.zhaolearn.shirostudy1.dao;

import com.zhaolearn.shirostudy1.domain.User;

public interface UserMapper {
 public User findByUserName(String userName);
    int deleteByPrimaryKey(Long id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
}