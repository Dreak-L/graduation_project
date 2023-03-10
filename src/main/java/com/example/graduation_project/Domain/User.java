package com.example.graduation_project.Domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@TableName("user")
public class User {
    @TableId(value = "userId",type = IdType.AUTO)
    private Integer id;
    private String username;
    private String password;
    private String userEmail;
    private Integer userGender;
    private String userIdcard;
    private Integer userRoleid;
    private Integer userAge;
    private Integer userDepart;
    private Integer userStateId;
    private String userDepartname;
    private String userRolename;
    private String userStatename;
    private Integer userDepartbelong;
    private String userDepartbelongname;
    private String userDesc;
    private String userPhoto;
    private String userPhotoRequest;

}
