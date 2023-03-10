package com.example.graduation_project.Service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.graduation_project.Domain.Patients;
import com.example.graduation_project.Domain.User;
import com.example.graduation_project.Mapper.PatientsMapper;
import com.example.graduation_project.Service.PatientsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientsServiceImpl implements PatientsService {

    @Autowired
    PatientsMapper patientsMapper;

    @Override
    public Patients findByPatientEmail(String patientEmail) {
        patientsMapper.frontupadtepatients();
        QueryWrapper<Patients> patientsQueryWrapper = new QueryWrapper<>();
        patientsQueryWrapper.eq("patient_email",patientEmail);
        Patients patient = patientsMapper.selectOne(patientsQueryWrapper);
        return patient;
    }

    @Override
    public Page<Patients> findAllPatients(Integer pn) {
        Page<Patients> patientsPage = new Page<>(pn, 10);
        Page<Patients> patientsPage1 = patientsMapper.selectPage(patientsPage, null);
        List<Patients> patients = patientsMapper.selectList(null);
        return patientsPage1;
    }

    @Override
    public int patientRegister(Patients patient) {
        int i = patientsMapper.insert(patient);
        return i;
    }

    @Override
    public Page<Patients> findPatientByState(String stateName, Integer pn, User loginuser) {
        Page<Patients> patientsPage = new Page<>(pn, 10);
        QueryWrapper<Patients> patientsQueryWrapper = new QueryWrapper<>();
        if (loginuser.getUserRoleid()==1||loginuser.getUserRoleid()==2||loginuser.getUserRoleid()==3){
            patientsQueryWrapper.eq("patient_state_name", stateName);
        }else  if (loginuser.getUserRoleid()==4||loginuser.getUserRoleid()==5){
            patientsQueryWrapper.eq("patient_state_name", stateName).eq("patient_depart_belong",loginuser.getUserDepartbelong());
        }else if (loginuser.getUserRoleid()==6||loginuser.getUserRoleid()==7) {
            patientsQueryWrapper.eq("patient_state_name", stateName).eq("patient_depart", loginuser.getUserDepart());
        }
        return patientsMapper.selectPage(patientsPage,patientsQueryWrapper);
    }

    @Override
    public Patients findPatientByid(Integer id) {
        return patientsMapper.selectById(id);
    }

    @Override
    public int updatePatient(Patients patients) {
        int i = patientsMapper.updateById(patients);
        return i;
    }

    @Override
    public int findAllPatientsNum() {
        return patientsMapper.selectList(null).size();
    }

    @Override
    public List<Patients> findPatientByIdcard(String s) {
        System.out.println(11111);
        QueryWrapper<Patients> patient_idcard = new QueryWrapper<Patients>().eq("patient_idcard", s);

        return patientsMapper.selectList(patient_idcard);
    }

    @Override
    public Patients findOnlyOneEmail(String email) {
        return patientsMapper.selectOne(new QueryWrapper<Patients>().eq("patient_email",email));
    }

    @Override
    public int deletepatient(Integer id) {
        int i = patientsMapper.deleteById(id);
        return i;
    }

}
