package com.example.graduation_project.Service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.graduation_project.Domain.DepartBelong;
import com.example.graduation_project.Domain.Department;
import com.example.graduation_project.Domain.User;

import java.util.List;

public interface DepartmentService {

    List<Department> findDepartmentAll();


    List<DepartBelong> findDepartBelong();

    Page<Department> findalldepartment(Integer pn);

    int addDepart(Department department);

    int updateDepart(Department department);

    Department findDepartByid(Integer id);

    int deleteDepartByid(Integer id);

    List<Department> findDepartmentByRole(User loginuser);

    int findDepartmentAllNum();
}
