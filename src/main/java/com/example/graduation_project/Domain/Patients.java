package com.example.graduation_project.Domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.beans.Transient;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("patients")
public class Patients {
    @TableId(value = "patient_id",type = IdType.AUTO)
    private Long id;
    private Integer patientGender;
    private String patientName;
    private String patientEmail;
    private String patientPassword;
    private String patientIdcard;
    private Integer patientStateId;
    private String patientStateName;

    @TableField(exist = false)
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date paInHospital;

    @TableField(exist = false)
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date paLeHospital;
    private Integer patientDepartBelong;
    private Integer patientDepart;

}
