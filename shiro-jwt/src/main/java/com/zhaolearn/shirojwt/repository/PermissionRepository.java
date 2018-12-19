package com.zhaolearn.shirojwt.repository;

import com.zhaolearn.shirojwt.domain.Permission;
import com.zhaolearn.shirojwt.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Set;

public interface PermissionRepository extends JpaRepository<Permission, Long>, JpaSpecificationExecutor<Permission> {
    @Query("select pt.permName from Permission pt,Role rt where pt.roleName=rt.roleName AND rt.userName=:canshu")
    String findPermByUserName(@Param("canshu") String canshu);

    Set<String> findPermissionsByRoleNameIn(Set<String> roleNames);

    Permission findPermissionByRoleName(String roleName);

    @Query("select pt.permName from Permission pt,Role rt where pt.roleName=rt.roleName AND rt.userName=:canshu")
    Set<String> findPermsByUserName(@Param("canshu") String canshu);
}
