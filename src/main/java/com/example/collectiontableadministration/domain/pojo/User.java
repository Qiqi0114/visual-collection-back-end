package com.example.collectiontableadministration.domain.pojo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

/**
 * @author 杨子涵
 * 用户实体类
 */
@Data
@TableName("sys_user")
public class User  {
    public User() {
    }

    public User(String userName, String userAccount, String passWord, int userSex, String userMail, String userPhone, Long departmentId, Long roleId) {
        this.userName = userName;
        this.userAccount = userAccount;
        this.passWord = passWord;
        this.userSex = userSex;
        this.userEmail = userMail;
        this.userPhone = userPhone;
        this.departmentId = departmentId;
        this.roleId = roleId;
    }

    /**
     * id
     */
    @TableId(type= IdType.ASSIGN_ID)
    private Long id;
    /**
     * 用户名
     */

    private  String userName;
    /**
     * 用户账号
     */
    private  String userAccount;
    /**
     * 用户密码
     */
    private String  passWord;

    /**
     * 用户性别
     */

    private int  userSex;
    /**
     * 用户邮箱
     */

    private String userEmail;
    /**
     * 用户电话
     */
    private String userPhone;
    /**
     * 系id
     */
    private Long departmentId;

    /**
     * 角色id
     */
    private Long roleId;




}
