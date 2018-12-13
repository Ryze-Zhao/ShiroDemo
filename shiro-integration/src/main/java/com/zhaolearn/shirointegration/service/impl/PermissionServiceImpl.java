package com.zhaolearn.shirointegration.service.impl;

import com.zhaolearn.shirointegration.domain.User;
import com.zhaolearn.shirointegration.repository.PermissionRepository;
import com.zhaolearn.shirointegration.repository.UserRepository;
import com.zhaolearn.shirointegration.service.PermissionService;
import com.zhaolearn.shirointegration.service.ShiroService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class PermissionServiceImpl implements PermissionService {
    @Autowired
    private PermissionRepository permissionRepository;


}
