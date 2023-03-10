package com.example.graduation_project.Service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.graduation_project.Domain.Role;
import com.example.graduation_project.Domain.User;
import com.example.graduation_project.Mapper.RoleMapper;
import com.example.graduation_project.Service.RolesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RolesServiceImpl implements RolesService {

    @Autowired
    RoleMapper roleMapper;
    @Override
    public Page<Role> findAllRole(Integer pn) {
        Page<Role> rolePage = new Page<>(pn, 20);
        Page<Role> rolePage1 = roleMapper.selectPage(rolePage, null);
        return rolePage1;
    }

    @Override
    public int addRole(Role role) {
        int insert = roleMapper.insert(role);
        return insert;
    }

    @Override
    public List<Role> findRoleName() {
        List<Role> roles = roleMapper.selectList(null);
        return roles;
    }

    @Override
    public List<Role> findLoginRoleList(User loginuser) {
        QueryWrapper<Role> roleId = new QueryWrapper<Role>();
        if (loginuser.getUserRoleid()==1)
            roleId.ge("roleId", loginuser.getUserRoleid());
        else
            roleId.gt("roleId", loginuser.getUserRoleid());
        List<Role> roles = roleMapper.selectList(roleId);
        return roles;
    }
}
