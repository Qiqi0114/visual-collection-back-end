package com.example.collectiontableadministration.domain.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author 32298
 */
@Data
@TableName("workload_correlation")
public class WorkloadCorrelation {
    @TableId(type= IdType.ASSIGN_ID)
    private Long id;
    private Long numberYearId;
    private Long userId;
    private Long departmentId;
    private String f;
    private String y1;
    private String y2;
    private String s1;
    private String z;

}
