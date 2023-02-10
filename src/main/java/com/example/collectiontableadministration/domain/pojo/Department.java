package com.example.collectiontableadministration.domain.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author yzh
 * 系实体类
 */
@Data
@TableName("sys_department")
public class Department {
    @TableId(type= IdType.ASSIGN_ID)
    private Long id;
    private String departmentName;
}
