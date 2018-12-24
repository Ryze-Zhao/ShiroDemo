package com.zhaolearn.shirointegration5.common.shiro;

import com.zhaolearn.shirointegration5.common.jwt.JWTToken;
import com.zhaolearn.shirointegration5.common.jwt.JWTUtil;
import com.zhaolearn.shirointegration5.domain.User;
import com.zhaolearn.shirointegration5.service.ShiroService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;


/**
 * @Info:自定义Realm
 * @author: HeHaoZhao
 * @date: 2018/12/20 10:22
 */
public class UserRealm extends AuthorizingRealm {
    private final static org.slf4j.Logger LOGGER = LoggerFactory.getLogger(UserRealm.class);
    @Autowired
    private ShiroService shiroService;

    /**
     * @param token
     * @return: boolean
     * @Info:  必须重写此方法，不然Shiro会报错,后果自负
     * @author: HeHaoZhao
     * @date: 2018/12/20 10:23
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JWTToken;
    }

    /**
     * 执行授权逻辑
     *
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("执行授权逻辑");
        //给资源进行授权
        String username = JWTUtil.getUserName(principalCollection.toString());
        User user = shiroService.findByUserName(username);
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        Set<String> roles = shiroService.findRolesByUserName(user.getUserName());
        simpleAuthorizationInfo.setRoles(roles);
        Set<String> permission = shiroService.findPermsByUserName(user.getUserName());
        simpleAuthorizationInfo.setStringPermissions(permission);
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
        System.out.println("执行认证逻辑");
        String token = (String) authenticationToken.getCredentials();
        // 解密获得username，用于和数据库进行对比
        String username = JWTUtil.getUserName(token);
        User user = shiroService.findByUserName(username);

        if (user == null) {
            //shiro底层会抛出UnknownAccountException，表示不存在用户
            return null;
        }
        //用于其他位置的验证
        if(!JWTUtil.verify(token,user.getUserName(),user.getPassWord())) {
            throw new IncorrectCredentialsException("Toekn不正确");
        }
        return new SimpleAuthenticationInfo(token, token, getClass().getName());
    }
}
