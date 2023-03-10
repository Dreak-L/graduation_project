package com.example.graduation_project.Domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName
public class Department {
    @TableId(value = "departID",type = IdType.AUTO)
    private Integer id;
    private String departname;
    private String departdesc;
    private Integer departbelong;
    private String departbelongname;
}
