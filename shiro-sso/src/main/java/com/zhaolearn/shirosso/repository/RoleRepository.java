package com.zhaolearn.shirosso.repository;

import com.zhaolearn.shirosso.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Set;

public interface RoleRepository extends JpaRepository<Role, Long>, JpaSpecificationExecutor<Role> {
    Role findByUserName(String userName);

    @Query("select rt.roleName from Role rt where rt.userName=:canshu")
    Set<String> findRolesByUserName(@Param("canshu") String canshu);
}
