package com.example.collectiontableadministration.domain.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("sys_wrokload")
public class Workload {
   @TableId(type= IdType.ASSIGN_ID)
   private Long id;
   private String teachCourse;
   private String openingSchool;
   private String schoolAccounting;
   private String natureCurriculum;
   private String teachingTerm;
   private String classRoom;
   private String k1;
   private String k2;
   private String k3;
   private String k4;
   private String k5;
   private String k6;
   private String j	;
   private String f	;
   private String r	;
   private String y1;
   private String y2;
   private String classRoom2;
   private String teachingContent;
   private String rStudent;
   private String jj;
   private String k7;
   private String s1;
   private Long workloadCorrelationId;

}
