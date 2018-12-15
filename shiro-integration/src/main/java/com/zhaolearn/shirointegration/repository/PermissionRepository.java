package com.zhaolearn.shirointegration.repository;

import com.zhaolearn.shirointegration.domain.Permission;
import com.zhaolearn.shirointegration.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PermissionRepository extends JpaRepository<Permission, Long>, JpaSpecificationExecutor<Permission> {
    @Query("select pt.permName from Permission pt,Role rt where pt.roleName=rt.roleName AND rt.userName=:canshu")
    String findPermByUserName(@Param("canshu") String canshu);

}
