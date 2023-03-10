package com.example.graduation_project.Service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.graduation_project.Domain.MedicalRecord;
import com.example.graduation_project.Domain.User;

import java.util.List;

public interface MedicalRocardService {
    Page<MedicalRecord> findAllMedicalRocard(Integer pn);

    Page<MedicalRecord> findMedicalRocardBydepart(String departname, Integer pn, User loginuser);

    Page<MedicalRecord> findMedicalBySelf(Integer pn, Integer medicalByid);

    int addMedicalRocard(MedicalRecord medicalRecord);

    int deleteMedicalByid(Integer id);

    MedicalRecord findMedicalById(Integer id);

    int updateMedical(MedicalRecord medicalRecord);

    Page<MedicalRecord> findPatientMedical(Integer pn, String idcard);

    List<MedicalRecord> findPatientMedicalByidcard(String patientIdcard);

    List<MedicalRecord> findAllMedicalRocardNum();

    Page<MedicalRecord> findPatientStateByMedicalInRole(Integer pn, User loginuser, String stateName);
}
