package com.zhaolearn.shirointegration.shiro;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {
    private final static Logger logger = LoggerFactory.getLogger(ShiroConfig.class);
    //创建ShiroFilterFactoryBean
    @Bean("shiroFilterFactoryBean")
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(
            @Qualifier("defaultWebSecurityManager") DefaultWebSecurityManager defaultWebSecurityManager) {
logger.info("jinlaile");
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(defaultWebSecurityManager);
        /*
         * 4    Shiro内置过滤器，可以实现权限相关的拦截器，常用过滤器
         * 4.1    anon：无需认证
         * 4.2    authc：必须认证
         * 4.3    user：如果使用rememberMe功能可以直接访问
         * 4.4    perms[]：该资源必须有该权限才可以访问
         * 4.5    role[]：该资源必须有该角色才可以访问
         */
        Map<String, String> filterMap = new LinkedHashMap<>(); //为了保证有序采用Linked，key是拦截路径，value是过滤器
        filterMap.put("/demo/index", "anon");
        filterMap.put("/demo/tologin", "anon");
        filterMap.put("/demo/login", "anon");
        filterMap.put("/demo/add", "perms[perm1]");
        //一个目录下可以使用这个，这个是controller路径
        filterMap.put("/demo/*", "authc");
        shiroFilterFactoryBean.setLoginUrl("/demo/tologin");//拦截后跳转到的页面
        shiroFilterFactoryBean.setUnauthorizedUrl("/demo/index");//未授权自动跳转到的页面
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterMap);
        return shiroFilterFactoryBean;
    }

    //创建DefaultWebSecurityManager
    @Bean("defaultWebSecurityManager")
    public DefaultWebSecurityManager getDefaultWebSecurityManager(@Qualifier("userRealm") UserRealm userRealm) {
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
        defaultWebSecurityManager.setRealm(userRealm);
        return defaultWebSecurityManager;
    }

    //创建Realm,返回的值放入Spring
    @Bean("userRealm")
    public UserRealm getRealm() {
        return new UserRealm();
    }


    /**
     * 配置ShiroDialect用于thymeleaf和shiro标签配合使用
     */
    @Bean("shiroDialect")
    public ShiroDialect getShiroDialect(){
        return new ShiroDialect();
    }

    /**
     * 注入加密方式一：
     * * 凭证匹配器
     * （由于我们的密码校验交给Shiro的SimpleAuthenticationInfo进行处理了 ）
     * @return
     */
   /* @Bean("hashedCredentialsMatcher")
    public HashedCredentialsMatcher hashedCredentialsMatcher(){
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        hashedCredentialsMatcher.setHashAlgorithmName("md5");//散列算法:这里使用MD5算法;
        //   hashedCredentialsMatcher.setHashIterations(2);//散列的次数，比如散列两次，相当于 md5(md5(""));
        hashedCredentialsMatcher.setHashIterations(1024);
        return hashedCredentialsMatcher;
    }*/


}
