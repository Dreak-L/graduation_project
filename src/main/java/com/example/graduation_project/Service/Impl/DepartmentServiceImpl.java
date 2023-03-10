package com.example.graduation_project.Service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.graduation_project.Domain.DepartBelong;
import com.example.graduation_project.Domain.Department;
import com.example.graduation_project.Domain.User;
import com.example.graduation_project.Mapper.DepartBelongMapper;
import com.example.graduation_project.Mapper.DepartmentMapper;
import com.example.graduation_project.Service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    DepartmentMapper departmentMapper;
    @Autowired
    DepartBelongMapper departBelongMapper;

    @Override
    public List<Department> findDepartmentAll() {

        List<Department> departmentList = departmentMapper.selectList(null);
        return departmentList;
    }

    @Override
    public List<DepartBelong> findDepartBelong() {
        List<DepartBelong> departBelongs = departBelongMapper.selectList(null);
        return departBelongs;
    }

    @Override
    public Page<Department> findalldepartment(Integer pn) {
        return departmentMapper.selectPage(new Page<Department>(pn, 10), null);
    }

    @Override
    public int addDepart(Department department) {
        int insert = departmentMapper.insert(department);
        return insert;
    }

    @Override
    public int updateDepart(Department department) {
        int i = departmentMapper.updateById(department);
        return i;
    }

    @Override
    public Department findDepartByid(Integer id) {
        Department department = departmentMapper.selectById(id);
        return department;
    }

    @Override
    public int deleteDepartByid(Integer id) {
        int i = departmentMapper.deleteById(id);
        return i;
    }

    @Override
    public List<Department> findDepartmentByRole(User loginuser) {
        QueryWrapper<Department> departmentQueryWrapper = new QueryWrapper<>();
        if (loginuser.getUserRoleid()==1||loginuser.getUserRoleid()==2||loginuser.getUserRoleid()==3){
            return departmentMapper.selectList(null);
        }else if (loginuser.getUserRoleid()==4||loginuser.getUserRoleid()==5){
            departmentQueryWrapper.eq("departbelong",loginuser.getUserDepartbelong());
        }else if (loginuser.getUserRoleid()==6||loginuser.getUserRoleid()==7){
            departmentQueryWrapper.eq("departID",loginuser.getUserDepart());
        }
        return departmentMapper.selectList(departmentQueryWrapper);
    }

    @Override
    public int findDepartmentAllNum() {

        return departmentMapper.selectList(null).size();
    }


}
