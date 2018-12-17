package com.zhaolearn.shirojwt.shiro;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class MyRoleAuthorizationFilter extends AuthorizationFilter {
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
