package com.lvr.ihave.business.service.impl;

import com.lvr.ihave.business.mapper.RoleMapper;
import com.lvr.ihave.business.service.RoleService;
import com.lvr.ihave.pojo.Roles;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public List<Roles> getAllRoles() {
        return roleMapper.selectAllRoles();
    }

    @Override
    public Roles getRoleById(String id) {
        return roleMapper.selectRoleById(id);
    }

    @Override
    public void updateRole(Roles role) {
        roleMapper.updateRole(role);
    }

    @Override
    public void addRole(Roles role) {
        role.setId(new Random().nextInt(10000));
        role.setCreateDate(LocalDate.now().toString());
        roleMapper.insertRole(role);
    }

     @Override
    public void deleteRole(String id) {
        roleMapper.deleteRole(id);
    }
}