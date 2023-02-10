package com.example.collectiontableadministration.domain.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author lhy
 */
@Data
@TableName("sys_number_year")
public class NumberYear {
    @TableId(type= IdType.ASSIGN_ID)
    private Long id;
    private String numberYears;
}
