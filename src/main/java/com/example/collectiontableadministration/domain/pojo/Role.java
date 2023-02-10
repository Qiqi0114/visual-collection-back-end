package com.example.collectiontableadministration.domain.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.example.collectiontableadministration.domain.comm.Basics;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;


/**
 * @author 杨子涵
 * 角色实体类
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("sys_role")
public class Role extends Basics implements Serializable {
    /**
     * 角色id
     */
    @TableId(type= IdType.ASSIGN_ID)
    private Long  id;
    /**
     * 角色姓名
     */
        private String roleName;


}
