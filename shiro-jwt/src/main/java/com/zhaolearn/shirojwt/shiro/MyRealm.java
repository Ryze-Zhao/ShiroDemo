package com.zhaolearn.shirojwt.shiro;

import com.zhaolearn.shirojwt.domain.User;
import com.zhaolearn.shirojwt.service.ShiroService;
import com.zhaolearn.shirojwt.utils.JWTUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Service
public class MyRealm extends AuthorizingRealm {

    private static final Logger LOGGER = LogManager.getLogger(MyRealm.class);
    @Autowired
    private ShiroService shiroService;


    /**
     * 大坑！，必须重写此方法，不然Shiro会报错
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }

    /**
     * 只有当需要检测用户权限的时候才会调用此方法，例如checkRole,checkPermission之类的
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        LOGGER.info("进入授权逻辑");
        String username = JWTUtil.getUsername(principals.toString());
        User user = shiroService.findByUserName(username);
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();

        String roleName = shiroService.findRoleByUserName(user.getUserName()).getRoleName();
        // Set<String> roleSet = shiroService.findRolesByUserName(user.getUserName());
        simpleAuthorizationInfo.addRole(roleName);
        Set<String> permNameSet = new HashSet<>(Arrays.asList(shiroService.findPermissionByRoleName(roleName).getPermName().split(",")));
        permNameSet.stream().forEach(e -> LOGGER.info(e));
        simpleAuthorizationInfo.addStringPermissions(permNameSet);
        return simpleAuthorizationInfo;
    }

    /**
     * 默认使用此方法进行用户名正确与否验证，错误抛出异常即可。
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken auth) throws AuthenticationException {
       LOGGER.info("进入认证逻辑");
        String token = (String) auth.getCredentials();
        // 解密获得username，用于和数据库进行对比
        String username = JWTUtil.getUsername(token);
        User user = shiroService.findByUserName(username);
        if (user == null) {
            //shiro底层会抛出UnknownAccountException，表示不存在用户
            return null;
        }
        //用于其他位置的验证
        if(!JWTUtil.verify(token,user.getUserName(),user.getPassWord())){
            throw new IncorrectCredentialsException("Toekn不正确");
        }
        return new SimpleAuthenticationInfo(token, token, getClass().getName());
    }
}
