package com.zhaolearn.shirojwtredis.repository;

import com.zhaolearn.shirojwtredis.domain.Permission;
import com.zhaolearn.shirojwtredis.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Set;

public interface PermissionRepository extends JpaRepository<Permission, Long>, JpaSpecificationExecutor<Permission> {
    @Query("select pt.permName from Permission pt,Role rt where pt.roleName=rt.roleName AND rt.userName=:canshu")
    String findPermByUserName(@Param("canshu") String canshu);

    @Query("select pt.permName from Permission pt,Role rt where pt.roleName=rt.roleName AND rt.userName=:canshu")
    Set<String> findPermsByUserName(@Param("canshu") String canshu);
}
