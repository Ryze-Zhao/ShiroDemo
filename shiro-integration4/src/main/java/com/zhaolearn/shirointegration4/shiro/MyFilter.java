package com.zhaolearn.shirointegration4.shiro;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * @Info:除了例外的URL，其他全部都要进入这个拦截器
 * @author: HeHaoZhao
 * @date: 2018/12/20 9:20
 */
public class MyFilter extends AuthorizationFilter {
    @Override
    protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse, Object value) throws Exception {
        Subject subject = getSubject(servletRequest, servletResponse);
        String[] rolesOrPerms = (String[]) value;
        if (rolesOrPerms == null || rolesOrPerms.length == 0) {
            return true;
        }
        for (String rolesOrPerm : rolesOrPerms) {
            if (subject.hasRole(rolesOrPerm)||subject.isPermitted(rolesOrPerm)) {
                return true;
            }
        }
        return false;
    }
}
