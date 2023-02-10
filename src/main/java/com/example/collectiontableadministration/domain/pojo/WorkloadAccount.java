package com.example.collectiontableadministration.domain.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.example.collectiontableadministration.domain.comm.Basics;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @author 杨子涵
 * 工作量导入表
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("sys_workload_account")
public class WorkloadAccount extends Basics implements Serializable {
    /**
     * id
     */
    @TableId(type= IdType.ASSIGN_ID)
    private Long id;
    /**
     * 讲授课程
     */
    @TableField("teach_courses")
    private String teachCourses;
    /**
     * 开课学院id
     */
    private String openCollegeName;
    /**
     * 核算学院id
     */
    private String accountCollegeName;
    /**
     * 课程性质id
     */
    private String courseNatureName;
    /**
     * 授课学期id
     */
    private String teachingTermName;
    /**
     * 班级名称
     */
    private String className;
    private String k1;
    private String k2;
    private String k3;
    private String k4;
    private String k5;
    private String k6;
    /**
     * 计划学时j
     */
    private  String  plannedHours;
    /**
     * 教学业绩分值F=（K1+K2+K3+K4+K5+K6)*J
     */
    private  String f;
    /**
     * r 人数
     */
    private  String r;
    /**
     * y 用于考核
     */
    private  String yAssessment;
    /**
     * y 用于津贴
     */
    private  String yAllowance;
    /**
     * 班级
     */
    private String  sClassName;
    /**
     * 教学内容
     */
    private String teachingContent;
    /**
     * 学生人数
     */
    private String rStudentNumber;
    /**
     * 计划周数
     */
    private String jPlanningWeeks;
    /**
     * k7
     */
    private String k7;
    /**
     * s1
     */
    private String s1;

    /**
     * 角色id
     */
    private Long userId;

}
