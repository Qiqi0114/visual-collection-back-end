package com.example.collectiontableadministration.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @author 32298
 */
@Data
public class UserDepartmentVo {
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


    private String departmentName;

    private String roleName;

}
