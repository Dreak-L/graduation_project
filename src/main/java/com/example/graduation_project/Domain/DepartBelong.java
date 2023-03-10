package com.example.graduation_project.Domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@TableName("departbelongto")
@AllArgsConstructor
@Data
@NoArgsConstructor
public class DepartBelong {
    @TableId(value = "departbelongid",type = IdType.AUTO)
    private Integer id;
    private String departbelongname;
}
