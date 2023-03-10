package com.example.graduation_project.Service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.graduation_project.Domain.MedicalRecord;
import com.example.graduation_project.Domain.User;
import com.example.graduation_project.Mapper.MedicalRocardMapper;
import com.example.graduation_project.Service.MedicalRocardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicalRocardServiceImpl implements MedicalRocardService {

    @Autowired
    MedicalRocardMapper medicalRocardMapper;

    @Override
    public Page<MedicalRecord> findAllMedicalRocard(Integer pn) {
        Page<MedicalRecord> medicalRecordPage = new Page<>(pn, 10);
        Page<MedicalRecord> recordPage = medicalRocardMapper.selectPage(medicalRecordPage, null);
        return recordPage;
    }

    @Override
    public Page<MedicalRecord> findMedicalRocardBydepart(String departname, Integer pn, User loginuser) {
        Page<MedicalRecord> medicalRecordPage = new Page<>(pn, 10);
        QueryWrapper<MedicalRecord> medicalRecordQueryWrapper = new QueryWrapper<>();
        if (loginuser.getUserRoleid()==1||loginuser.getUserRoleid()==2||loginuser.getUserRoleid()==3){
            QueryWrapper<MedicalRecord> caseBydepart = medicalRecordQueryWrapper.eq("case_bydepart", departname);
            Page<MedicalRecord> recordPage = medicalRocardMapper.selectPage(medicalRecordPage, caseBydepart);
            return recordPage;
        }else if (loginuser.getUserRoleid()==4||loginuser.getUserRoleid()==5){
            QueryWrapper<MedicalRecord> caseBydepart = medicalRecordQueryWrapper.eq("case_bydepart", departname).eq("case_bydepart_belong",loginuser.getUserDepartbelong());
            Page<MedicalRecord> recordPage = medicalRocardMapper.selectPage(medicalRecordPage, caseBydepart);
            return recordPage;
        }else if (loginuser.getUserRoleid()==6||loginuser.getUserRoleid()==7||loginuser.getUserRoleid()==8){
            if (departname.equals(loginuser.getUserDepartname())){
                QueryWrapper<MedicalRecord> caseBydepart = medicalRecordQueryWrapper.eq("case_bydepart", departname);
                Page<MedicalRecord> recordPage = medicalRocardMapper.selectPage(medicalRecordPage, caseBydepart);
                return recordPage;
            }else return null;
        }else
            return null;
    }

    @Override
    public Page<MedicalRecord> findMedicalBySelf(Integer pn, Integer medicalByid) {
        Page<MedicalRecord> medicalRecordPage = new Page<>(pn, 10);
        QueryWrapper<MedicalRecord> medicalQuerywrapper = new QueryWrapper<>();
        QueryWrapper<MedicalRecord> case_bydoctorid = medicalQuerywrapper.eq("case_bydoctorid", medicalByid);
        Page<MedicalRecord> medicalRecordPage1 = medicalRocardMapper.selectPage(medicalRecordPage, case_bydoctorid);
        return medicalRecordPage1;
    }

    @Override
    public int addMedicalRocard(MedicalRecord medicalRecord) {
        int insert = medicalRocardMapper.insert(medicalRecord);
        return insert;
    }

    @Override
    public int deleteMedicalByid(Integer id) {
        int i = medicalRocardMapper.deleteById(id);
        return i;
    }

    @Override
    public MedicalRecord findMedicalById(Integer id) {
        MedicalRecord medicalRecord = medicalRocardMapper.selectById(id);
        return medicalRecord;
    }

    @Override
    public int updateMedical(MedicalRecord medicalRecord) {
        int update = medicalRocardMapper.updateById(medicalRecord);
        return update;
    }

    @Override
    public Page<MedicalRecord> findPatientMedical(Integer pn, String idcard) {
        QueryWrapper<MedicalRecord> case_idcard = new QueryWrapper<MedicalRecord>().eq("case_idcard", idcard);
        Page<MedicalRecord> medicalRecordPage = medicalRocardMapper.selectPage(new Page<MedicalRecord>(pn, 10), case_idcard);
        return medicalRecordPage;
    }

    @Override
    public List<MedicalRecord> findPatientMedicalByidcard(String patientIdcard) {
        QueryWrapper<MedicalRecord> case_idcard = new QueryWrapper<MedicalRecord>().eq("case_idcard", patientIdcard);
        return medicalRocardMapper.selectList(case_idcard);
    }

    @Override
    public List<MedicalRecord> findAllMedicalRocardNum() {
        List<MedicalRecord> medicalRecords = medicalRocardMapper.selectList(null);
        return medicalRecords;
    }

    @Override
    public Page<MedicalRecord> findPatientStateByMedicalInRole(Integer pn, User loginuser, String stateName) {
        QueryWrapper<MedicalRecord> medicalRecordQueryWrapper = new QueryWrapper<>();
        if (loginuser.getUserRoleid()==1||loginuser.getUserRoleid()==2||loginuser.getUserRoleid()==3){
            medicalRecordQueryWrapper.eq("case_patient_statename",stateName);
        }
        if (loginuser.getUserRoleid()==4||loginuser.getUserRoleid()==5){
            medicalRecordQueryWrapper.eq("case_patient_statename",stateName).eq("case_bydepart_belong",loginuser.getUserDepartbelong());
        }
        if (loginuser.getUserRoleid()==6||loginuser.getUserRoleid()==7){
            medicalRecordQueryWrapper.eq("case_patient_statename",stateName).eq("case_bydepartid",loginuser.getUserDepart());
        }
        if (loginuser.getUserRoleid()==8){
            medicalRecordQueryWrapper.eq("case_patient_statename",stateName).eq("case_bydoctorid",loginuser.getId());
        }
        return medicalRocardMapper.selectPage(new Page<MedicalRecord>(pn,10),medicalRecordQueryWrapper);
    }

}
