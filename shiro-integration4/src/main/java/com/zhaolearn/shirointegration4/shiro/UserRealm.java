package com.zhaolearn.shirointegration4.shiro;

import com.zhaolearn.shirointegration4.domain.User;
import com.zhaolearn.shirointegration4.service.ShiroService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

/**
 * 自定义Realm
 */
public class UserRealm extends AuthorizingRealm {
    private final static org.slf4j.Logger LOGGER = LoggerFactory.getLogger(UserRealm.class);
    @Autowired
    private ShiroService shiroService;

    /**
     * 执行授权逻辑
     *
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        LOGGER.info("进入授权逻辑");
        String username = JwtUtil.getUsername(principalCollection.toString());
        User user = shiroService.findByUserName(username);
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        Set<String> permSet = shiroService.findPermsByUserName(user.getUserName());
        simpleAuthorizationInfo.setStringPermissions(permSet);
        Set<String> roleSet = shiroService.findRolesByUserName(user.getUserName());
        simpleAuthorizationInfo.setRoles(roleSet);
        return simpleAuthorizationInfo;
    }

    /**
     * 执行认证逻辑
     *
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        LOGGER.info("进入认证逻辑");
        String token = (String) authenticationToken.getCredentials();
        // 解密获得username，用于和数据库进行对比
        String username = JwtUtil.getUsername(token);
        User user = shiroService.findByUserName(username);
        if (user == null) {
            //shiro底层会抛出UnknownAccountException，表示不存在用户
            return null;
        }
        //用于其他位置的验证
        if(!JwtUtil.verify(token,user.getUserName(),user.getPassWord())){
            throw new IncorrectCredentialsException("Toekn不正确");
        }
        return new SimpleAuthenticationInfo(token, token, getClass().getName());
    }

}
