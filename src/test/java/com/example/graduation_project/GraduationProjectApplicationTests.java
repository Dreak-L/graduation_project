package com.example.graduation_project;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.graduation_project.Domain.*;
import com.example.graduation_project.Mapper.DepartmentMapper;
import com.example.graduation_project.Mapper.PatientsMapper;
import com.example.graduation_project.Mapper.UserMapper;
import com.example.graduation_project.Service.DepartmentService;
import com.example.graduation_project.Service.MedicalRocardService;
import com.example.graduation_project.Service.StatesService;
import com.example.graduation_project.Service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class GraduationProjectApplicationTests {

    @Autowired
    PatientsMapper patientsMapper;
    @Autowired
    DepartmentService departmentService;
    @Autowired
    DepartmentMapper departmentMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    UserService userService;
    @Autowired
    StatesService statesService;
    @Autowired
    MedicalRocardService medicalRocardService;
    @Test
    void contextLoads() {
//        QueryWrapper<Patients> patientsQueryWrapper = new QueryWrapper<>();
//        patientsQueryWrapper.eq("patient_email","888@147");
//        Patients patients = patientsMapper.selectOne(patientsQueryWrapper);
//        System.out.println(patients);
//        List<Department> departs = departmentService.findDepartmentAll();

//        List<Department> departs = departmentService.findDepartmentAll();
//        String[] depart = new String[departs.size()];
//        for (int i=0;i<departs.size();i++){
//            Department department = departs.get(i);
//            depart[i]=department.getDepartname();
//            System.out.println(depart[i]);
//        }
        /*userMapper.frontupdateDoctor();
        List<User> allDoctors = userService.getAllDoctors();
        for (User allDoctor : allDoctors) {
            System.out.println(allDoctor);
        }*/
        List<MedicalRecord> allMedicalRocardNum = medicalRocardService.findAllMedicalRocardNum();
        System.out.println(allMedicalRocardNum.size());
    }

    @Test
    void test(){
//        List<Department> departs = departmentService.findDepartmentAll();
//        String[] departname = null;
//        int i=0;
//        for (Department depart : departs) {
//            departname[i]=depart.getDepartname();
//            System.out.println(departname[i]);
//            i++;
//        }i
        MedicalRecord medicalById =
                medicalRocardService.findMedicalById(49);
        System.out.println("-------------");
        System.out.println(medicalById.getCaseFile().length());

    }
    @Test
    void testadduser(){
        User user = new User();
        user.setUserAge(44);
        user.setUserIdcard("412725133336359456");
        user.setUserEmail("111@25");
        user.setUserRoleid(3);
        user.setUserDepart(6);
        user.setUserStateId(4);
        user.setUsername("张辉");
        user.setPassword("zz123");
        for (int i = 0; i < 20; i++) {
            userService.userRegister(user);
        }
    }
    @Test
    void testdepartbyname(){
        QueryWrapper<User> userQueryWrapperBydepartname = new QueryWrapper<>();
        userQueryWrapperBydepartname.eq("user_departname","妇产科");
        Page<User> userPage = new Page<>();
        Page<User> userPage1 = userMapper.selectPage(userPage, userQueryWrapperBydepartname);
        List<User> records = userPage1.getRecords();
        for (User record : records) {
            System.out.println(record);
        }
    }
}
