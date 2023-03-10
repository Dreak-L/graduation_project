package com.example.graduation_project.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.graduation_project.Domain.User;

import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface UserMapper extends BaseMapper<User> {

    @Update("Update user,department set user.user_departbelongname=department.departbelongname where user.user_departbelong=department.departbelong")
    void frontupdateDoctor();

}
