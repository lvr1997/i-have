package com.lvr.ihave.business.service;

import com.lvr.ihave.pojo.Roles;

import java.util.List;

public interface RoleService {
    
    /**
     * 获取所有角色列表
     * @return 角色列表
     */
    List<Roles> getAllRoles();

    Roles getRoleById(String id);

    void updateRole(Roles role);

    void addRole(Roles role);

    void deleteRole(String id);
}