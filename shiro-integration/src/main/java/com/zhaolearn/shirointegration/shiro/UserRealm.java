package com.zhaolearn.shirointegration.shiro;

import com.zhaolearn.shirointegration.domain.User;
import com.zhaolearn.shirointegration.service.ShiroService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 自定义Realm
 */
public class UserRealm extends AuthorizingRealm {
    private final static org.slf4j.Logger log = LoggerFactory.getLogger(UserRealm.class);
    @Autowired
    private ShiroService shiroService;
    /**
     * 执行授权逻辑
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("执行授权逻辑");//给资源进行授权
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        //获取当前用户
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        //到数据库查询当前登录用户的授权字符串
        String perm = shiroService.findPermByUserName(user.getUserName());//通过当前登录用户id查找的数据库用户
        log.info("-------------------" + perm);
        info.addStringPermission(perm);
        String role=shiroService.findRoleByUserName(user.getUserName()).getRoleName();
        log.info("-------------------" + role);
        info.addRole(role);
        return info;
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
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        User user = shiroService.findByUserName(token.getUsername());
        if (user == null) {
            return null;//shiro底层会抛出UnknownAccountException，表示不存在用户
        }
        log.info("-------------------" + user.toString());
        //3、判断密码,AuthenticationInfo的子类SimpleAuthenticationInfo,
        // 第一个参数放入参数是为了上边授权逻辑的User user = (User) subject.getPrincipal();能拿到
        return new SimpleAuthenticationInfo(user, user.getPassWord(), "");
    }
}
