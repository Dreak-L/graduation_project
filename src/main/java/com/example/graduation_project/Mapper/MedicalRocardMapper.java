package com.example.graduation_project.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.graduation_project.Domain.MedicalRecord;
import org.apache.ibatis.annotations.Update;

public interface MedicalRocardMapper extends BaseMapper<MedicalRecord> {
    @Update("update states, medical_record set medical_record.case_patient_statename=states.patientstate where medical_record.case_patient_stateid=states.stateid")
    void frontupadteMedical();
}
