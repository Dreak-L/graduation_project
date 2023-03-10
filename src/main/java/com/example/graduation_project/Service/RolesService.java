package com.example.graduation_project.Service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.graduation_project.Domain.Role;
import com.example.graduation_project.Domain.User;

import java.util.List;

public interface RolesService {
    Page<Role> findAllRole(Integer pn);

    int addRole(Role role);

    List<Role> findRoleName();

    List<Role> findLoginRoleList(User loginuser);

}
