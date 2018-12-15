package com.zhaolearn.shirointegration.repository;

import com.zhaolearn.shirointegration.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;

public interface RoleRepository extends JpaRepository<Role, Long>, JpaSpecificationExecutor<Role> {
    Role findByUserName(String userName);

    @Query("select rt.roleName from Role rt where rt.userName=:userName")
    Set<String> findRolesByUserName(String userName);
}
