package com.zhaolearn.shirointegration5.common.config;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import com.zhaolearn.shirointegration5.common.shiro.MyCacheManager;
import com.zhaolearn.shirointegration5.common.shiro.MyFilter;
import com.zhaolearn.shirointegration5.common.shiro.UserRealm;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.DelegatingFilterProxy;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Info:Shiro的配置
 * @author: HeHaoZhao
 * @date: 2018/12/20 10:17
 */
@Configuration
public class ShiroConfig {
    private final static Logger logger = LoggerFactory.getLogger(ShiroConfig.class);

    @Bean("filterRegistrationBean")
    public FilterRegistrationBean delegatingFilterProxy(){
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        DelegatingFilterProxy proxy = new DelegatingFilterProxy();
        proxy.setTargetFilterLifecycle(true);
        proxy.setTargetBeanName("shiroFilterFactoryBean");
        filterRegistrationBean.setFilter(proxy);
        return filterRegistrationBean;
    }


    /**
     * @param defaultWebSecurityManager
     * @return: org.apache.shiro.spring.web.ShiroFilterFactoryBean
     * @Info:创建ShiroFilterFactoryBean
     * @author: HeHaoZhao
     * @date: 2018/12/20 10:18
     */
    @Bean("shiroFilterFactoryBean")
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(
            @Qualifier("defaultWebSecurityManager") DefaultWebSecurityManager defaultWebSecurityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(defaultWebSecurityManager);
        // 添加自己的过滤器并且取名为myFilter
        Map<String, Filter> filterMap = new LinkedHashMap<>();
        filterMap.put("myFilter", new MyFilter());
        shiroFilterFactoryBean.setFilters(filterMap);
        Map<String, String> filterRuleMap = new LinkedHashMap<>();
        // 访问401和404页面不通过我们的Filter
        filterRuleMap.put("/401", "anon");
        // 所有请求通过我们自己的JWT Filter
        filterRuleMap.put("/**", "myFilter");
        shiroFilterFactoryBean.setUnauthorizedUrl("/401");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterRuleMap);
        return shiroFilterFactoryBean;
    }

    /**
     * @param userRealm
     * @return: org.apache.shiro.web.mgt.DefaultWebSecurityManager
     * @Info:创建DefaultWebSecurityManager
     * @author: HeHaoZhao
     * @date: 2018/12/20 10:18
     */
    @Bean("defaultWebSecurityManager")
    public DefaultWebSecurityManager getDefaultWebSecurityManager(@Qualifier("userRealm") UserRealm userRealm) {
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
        defaultWebSecurityManager.setRealm(userRealm);
        // 关闭Shiro自带的session
        DefaultSubjectDAO subjectDAO = new DefaultSubjectDAO();
        DefaultSessionStorageEvaluator defaultSessionStorageEvaluator = new DefaultSessionStorageEvaluator();
        defaultSessionStorageEvaluator.setSessionStorageEnabled(false);
        subjectDAO.setSessionStorageEvaluator(defaultSessionStorageEvaluator);
        defaultWebSecurityManager.setSubjectDAO(subjectDAO);
        // 设置自定义缓存(Cache)管理器
        defaultWebSecurityManager.setCacheManager(new MyCacheManager());
        return defaultWebSecurityManager;
    }

    /**
     * @param
     * @return: com.zhaolearn.shirojwtredis.shiro.UserRealm
     * @Info:创建Realm,返回的值放入Spring
     * @author: HeHaoZhao
     * @date: 2018/12/20 10:18
     */
    @Bean("userRealm")
    public UserRealm getRealm() {
        return new UserRealm();
    }


    /**
     * 配置ShiroDialect用于thymeleaf和shiro标签配合使用
     */
    @Bean("shiroDialect")
    public ShiroDialect getShiroDialect() {
        return new ShiroDialect();
    }

    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(DefaultWebSecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }

}
