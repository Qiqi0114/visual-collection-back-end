package com.example.collectiontableadministration.domain.response.vo;

import com.example.collectiontableadministration.domain.pojo.WorkloadAccount;
import lombok.Data;

import java.util.List;

/**
 * @author 杨子涵
 * 工作量表 excl返回映射类
 */
@Data
public class WorkLadAccountExcel {
    private Long id;
    /**
     * 用户名
     */
    private  String userName;
    /**
     * 用户账号
     */
    private  String userAccount;

    private List<WorkloadAccount> workloadAccountList;

    /**
     * f 合计
     */
    private String fTotal;

    /**
     * Y（用于考核） 合计
     */
    private String yAssessmentTotal;

    /**
     * Y（计算津贴） 合计
     */
    private String yAllowanceTotal;

    private String sTotal;
    private String zTotal;
}
