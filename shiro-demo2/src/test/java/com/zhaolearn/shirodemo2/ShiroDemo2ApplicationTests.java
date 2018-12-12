package com.zhaolearn.shirodemo2;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ShiroDemo2ApplicationTests {
    private static final transient Logger log =LoggerFactory.getLogger(ShiroDemo2ApplicationTests.class);
    @Test
    public void shiroDemo2ApplicationTests() {
        //创建 org.apache.shiro.mgt.SecurityManager,切忌java.lang.SecurityManager
        //1、加载配置文件
        Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro.ini");
        //2、解析配置文件，并返回SecurityManager实例
        SecurityManager securityManager = factory.getInstance();
        //3、将SecurityManager绑定给到securityManager
        SecurityUtils.setSecurityManager(securityManager);
        //4、安全操作，Subject就是当前登录的用户
        Subject currentUser = SecurityUtils.getSubject();
        //测试在应用的当前会话中设置的属性
        Session session = currentUser.getSession();
        //放进去一个key和value
        session.setAttribute("someKey", "aValue");
        //根据key获取value
        String value = (String) session.getAttribute("someKey");
        //比较拿到的值和原来的值是否一致
        if ("aValue".equals(value)) {
            log.info("检索到正确的值[" + value + "]");
        }
        //尝试进行登录用户，如果登录失败了，我们进行一些处理
        if (!currentUser.isAuthenticated()) { //如果用户没有登录过
            UsernamePasswordToken token = new UsernamePasswordToken("test", "123456");
            token.setRememberMe(true);  //是否记住用户，是true，否false（rememberMe只能记住你登录过，但不会记住你是谁以及你的权限信息。）
            try {
                currentUser.login(token);   //当我们获登录用户之后
                log.info("用户 [" + currentUser.getPrincipal() + "] 登陆成功");
                // 查看用户是否有指定的角色
                if (currentUser.hasRole("admin")) {
                    log.info("您有admin角色");
                } else {
                    log.info("您没有admin角色");
                }
                if (currentUser.hasRole("role1")) {
                    log.info("您有role1角色");
                } else {
                    log.info("您没有role1角色");
                }
                // 查看用户是否有某个权限
                if (currentUser.isPermitted("perm1")) {
                    log.info("您有perm1权限");
                } else {
                    log.info("您没有perm1权限");
                }
                if (currentUser.isPermitted("guest")) {
                    log.info("您有guest权限");
                } else {
                    log.info("您没有guest权限");
                }
                //退出
                currentUser.logout();
            } catch (UnknownAccountException uae) {
                log.info(token.getPrincipal() + "账户不存在");
            } catch (IncorrectCredentialsException ice) {
                log.info(token.getPrincipal() + "密码不正确");
            } catch (LockedAccountException lae) {
                log.info(token.getPrincipal() + "用户被锁定了 ");
            } catch (AuthenticationException ae) {
                //无法判断是什么错了
                log.info(ae.getMessage());
            }
        }
    }

}
