package com.example.graduation_project.Domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;


import java.text.SimpleDateFormat;
import java.util.Date;


@AllArgsConstructor
@NoArgsConstructor
@Data
@TableName("medical_record")
public class MedicalRecord {
    @TableId(value = "id" ,type = IdType.AUTO)
    private Integer id;

    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date caseTime;

    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date caseInHospital;

    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date caseLeHospital;

    private Integer caseBydoctorid;
    private String caseBydoctorname;
    private String caseUsername;
    private Integer caseAge;
    private Integer caseGender;
    private String caseAddress;
    private Integer casePatientStateid;
    private String casePatientStatename;
    private String caseContent;



    private String caseFile;
    private String caseIdcard;
    private Long casePhoneNumer;
    private String caseUseDrug;
    private String caseBydepart;
    private Integer caseBydepartBelong;
    private Integer caseBydepartid;

    public String formateTime(Date date) {
        SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd" );
        return df.format(date);
    }

    /*public String getCaseFile() {
        if (caseFile==null)
            return "";
        return "data:image/png;base64,"+new String(caseFile);
    }*/

    /*public void setCaseFile(String caseFile) {
        this.caseFile = caseFile.getBytes();
    }*/
}
