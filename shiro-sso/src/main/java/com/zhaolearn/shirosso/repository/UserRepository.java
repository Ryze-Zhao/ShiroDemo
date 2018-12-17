package com.zhaolearn.shirointegration2.repository;

import com.zhaolearn.shirointegration2.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
    User findByUserName(String username);
}
