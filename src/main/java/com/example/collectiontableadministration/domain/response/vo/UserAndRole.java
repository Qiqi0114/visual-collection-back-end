package com.example.collectiontableadministration.domain.response.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

/**
 * @author 杨子涵
 * 用户角色映射类
 */
@Data
public class UserAndRole {
    /**
     * id
     */
    private  Long id;
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
     * 角色id
     */
    private Long roleId;
    /**
     * 角色姓名
     */
    private String roleName;
}
