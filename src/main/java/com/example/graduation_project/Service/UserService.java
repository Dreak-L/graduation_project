package com.example.graduation_project.Service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.graduation_project.Domain.User;

import java.util.List;

public interface UserService {

    User findByuserEmail(String userEmail);

    int userRegister(User user);

    List<User> getAllDoctors();

    Page<User> userRecordPage(Integer pn);

    Page<User> findDepartUserByName(String departname, Integer pn, User loginuser);


    Page<User> findDoctorByState(String stateName, User loginuser, Integer pn);

    User findDoctorByid(Integer id);

    int updateDoctorBydepartway(User user);

    int updateDoctor(User user);

    int deleteDoctor(Integer id);

    int getAllDoctorsNum();

    List<User> findDoctorRole(int i);
}
