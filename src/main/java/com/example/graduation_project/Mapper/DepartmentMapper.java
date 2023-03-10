package com.example.graduation_project.Mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.graduation_project.Domain.Department;
import org.apache.ibatis.annotations.Update;

public interface DepartmentMapper extends BaseMapper<Department> {

    @Update("update department,departbelongto set department.departbelongname=departbelongto.departbelongname where departbelongto.departbelongid=departbelong")
    void frontupDepart();
}
