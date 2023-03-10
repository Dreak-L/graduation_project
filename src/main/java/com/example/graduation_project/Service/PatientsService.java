package com.example.graduation_project.Service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.graduation_project.Domain.Patients;
import com.example.graduation_project.Domain.User;

import java.util.List;

public interface PatientsService {

    Patients findByPatientEmail(String patientEmail);

    Page<Patients> findAllPatients(Integer pn);

    int patientRegister(Patients patient);


    Page<Patients> findPatientByState(String stateName, Integer pn, User loginuser);

    Patients findPatientByid(Integer id);

    int updatePatient(Patients patients);

    int findAllPatientsNum();

    List<Patients> findPatientByIdcard(String s);

    Patients findOnlyOneEmail(String email);

    int deletepatient(Integer id);
}
