package com.zhaolearn.shirostudy1.shiro;

import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {
    //创建ShiroFilterFactoryBean
    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(
            @Qualifier("defaultWebSecurityManager") DefaultWebSecurityManager defaultWebSecurityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(defaultWebSecurityManager);
        /**
         * 4    Shiro内置过滤器，可以实现权限相关的拦截器
         * 常用过滤器
         * 4.1    anon：无需认证
         * 4.2    authc：必须认证
         * 4.3    user：如果使用rememberMe功能可以直接访问
         * 4.4    perms：该资源必须有该权限才可以访问
         * 4.5    role：该资源必须有该角色才可以访问
         */
        //为了保证有序采用Linked，key是拦截路径，value是
        Map<String, String> filterMap = new LinkedHashMap<>();
      /*  filterMap.put("/study1/add", "authc");
        filterMap.put("/study1/update", "authc");*/

        //但是我们仍需要login和test页面，所以要放行，这个是controller路径
        filterMap.put("/study1/test", "anon");
        filterMap.put("/study1/login", "anon");
        filterMap.put("/study1/loginUser", "anon");
        //一个目录下可以使用这个，这个是controller路径
        filterMap.put("/study1/*", "authc");

        shiroFilterFactoryBean.setLoginUrl("/study1/login");
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
}
