package com.example.graduation_project.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.graduation_project.Domain.Patients;
import org.apache.ibatis.annotations.Update;

public interface PatientsMapper extends BaseMapper<Patients> {
    @Update("update patients,states set patients.patient_state_name=states.patientstate where patients.patient_state_id=states.stateid")
    void frontupadtepatients();
}
