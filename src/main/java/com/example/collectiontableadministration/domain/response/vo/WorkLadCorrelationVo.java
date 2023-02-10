package com.example.collectiontableadministration.domain.response.vo;

import com.example.collectiontableadministration.domain.pojo.Workload;
import lombok.Data;

import java.util.List;

/**
 * @author 32298
 */
@Data
public class WorkLadCorrelationVo {
    private Long id;
    private String numberYears;
    private String userName;
    private String departmentName;
    private String f;
    private String y1;
    private String y2;
    private String s1;
    private String z;
    private List<Workload> workloadList;
}
