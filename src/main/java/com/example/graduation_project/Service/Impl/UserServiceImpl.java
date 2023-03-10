package com.example.graduation_project.Service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.graduation_project.Domain.User;
import com.example.graduation_project.Mapper.DepartmentMapper;
import com.example.graduation_project.Mapper.MedicalRocardMapper;
import com.example.graduation_project.Mapper.UserMapper;
import com.example.graduation_project.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;
    @Autowired
    DepartmentMapper departmentMapper;
    @Autowired
    MedicalRocardMapper medicalRocardMapper;

    @Override
    public User findByuserEmail(String userEmail) {
        userMapper.frontupdateDoctor();
        departmentMapper.frontupDepart();
        medicalRocardMapper.frontupadteMedical();
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("user_email",userEmail);
        User user = userMapper.selectOne(userQueryWrapper);
        return user;
    }

    @Override
    public int userRegister(User user) {

        return userMapper.insert(user);
    }

    @Override
    public List<User> getAllDoctors() {
        List<User> doctor = userMapper.selectList(null);
        return doctor;
    }

    public Page<User> userRecordPage(Integer pn) {
        Page<User> userPage = new Page<>(pn,10);
        Page<User> userPage1 = userMapper.selectPage(userPage, null);
        return userPage1;
    }

    @Override
    public Page<User> findDepartUserByName(String departname, Integer pn, User loginuser) {
        if (loginuser.getUserRoleid() == 1 || loginuser.getUserRoleid() == 2 || loginuser.getUserRoleid() == 3) {
            QueryWrapper<User> userQueryWrapperBydepartname = new QueryWrapper<>();
            userQueryWrapperBydepartname.eq("user_departname", departname);
            Page<User> userPage = new Page<>(pn, 10);
            Page<User> userPage1 = userMapper.selectPage(userPage, userQueryWrapperBydepartname);
            return userPage1;
        } else if (loginuser.getUserRoleid() == 4 || loginuser.getUserRoleid() == 5 || loginuser.getUserRoleid() == 6 || loginuser.getUserRoleid() == 7) {
            QueryWrapper<User> userQueryWrapperBydepartname = new QueryWrapper<>();
            userQueryWrapperBydepartname.eq("user_departname", departname).ge("user_roleid", loginuser.getUserRoleid());
            Page<User> userPage = new Page<>(pn, 10);
            Page<User> userPage1 = userMapper.selectPage(userPage, userQueryWrapperBydepartname);
            return userPage1;
        } else
            return null;
    }

    @Override
    public Page<User> findDoctorByState(String stateName, User loginuser, Integer pn) {

        if (loginuser.getUserRoleid()==1||loginuser.getUserRoleid()==2||loginuser.getUserRoleid()==3){
            System.out.println(1);
            QueryWrapper<User> user_statename = new QueryWrapper<User>().eq("user_statename", stateName);
            Page<User> userPage = new Page<>(pn, 10);
            return userMapper.selectPage(userPage,user_statename);
        }else if (loginuser.getUserRoleid()==4||loginuser.getUserRoleid()==5){
            System.out.println(2);
            QueryWrapper<User> user_statename = new QueryWrapper<User>().eq("user_statename", stateName).eq("user_departbelong", loginuser.getUserDepartbelong()).ge("user_roleid", loginuser.getUserRoleid());
            Page<User> userPage = new Page<>(pn, 10);
            return userMapper.selectPage(userPage,user_statename);
        }else if (loginuser.getUserRoleid()==6||loginuser.getUserRoleid()==7){
            System.out.println(3);
            QueryWrapper<User> user_statename = new QueryWrapper<User>().eq("user_statename", stateName).eq("user_depart", loginuser.getUserDepart()).ge("user_roleid", loginuser.getUserRoleid());
            Page<User> userPage = new Page<>(pn, 10);
            return userMapper.selectPage(userPage,user_statename);
        }else
            return null;

    }

    @Override
    public User findDoctorByid(Integer id) {
        User user = userMapper.selectById(id);
        return user;
    }

    @Override
    public int updateDoctorBydepartway(User user) {
        int i = userMapper.updateById(user);
        return i;
    }

    @Override
    public int updateDoctor(User user) {
        int i = userMapper.updateById(user);
        return i;
    }

    @Override
    public int deleteDoctor(Integer id) {
        int i = userMapper.deleteById(id);
        return i;
    }

    @Override
    public int getAllDoctorsNum() {
        List<User> users = userMapper.selectList(null);
        return users.size();
    }

    @Override
    public List<User> findDoctorRole(int i) {
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        if (i==1){
            userQueryWrapper.eq("user_roleid",2).or().eq("user_roleid",3);
        }else if (i==2){
            userQueryWrapper.eq("user_roleid",4).or().eq("user_roleid",5);

        }else if (i==3){
            userQueryWrapper.eq("user_roleid",6).or().eq("user_roleid",7);
        }
        return userMapper.selectList(userQueryWrapper);
    }
}
