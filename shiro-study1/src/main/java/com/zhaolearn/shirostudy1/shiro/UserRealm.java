package com.zhaolearn.shirostudy1.shiro;

import com.zhaolearn.shirostudy1.domain.User;
import com.zhaolearn.shirostudy1.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 自定义Realm
 */
public class UserRealm extends AuthorizingRealm {
    /**
     * 执行授权逻辑
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("执行授权逻辑");
        return null;
    }
    /**
     * 执行认证逻辑
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("执行认证逻辑");
     /*   //假设数据库中用户表的账号密码
        String username = "user";
        String password = "123456";*/
        //1、将authenticationToken转换为UsernamePasswordToken
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        User user=new User();

        //2、判断用户名
        if (user==null) {
            return null;//shiro底层会抛出UnknownAccountException，表示不存在用户
        }
        //3、判断密码,AuthenticationInfo的子类SimpleAuthenticationInfo
        return new SimpleAuthenticationInfo("",user.getPassword(),"");
    }
}
