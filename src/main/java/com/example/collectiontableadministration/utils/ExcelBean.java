package com.example.collectiontableadministration.utils;

import com.example.collectiontableadministration.domain.pojo.WorkloadAccount;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

/**
 * excel bean转换  你学不会的
 * @author 杨子涵
 */
public class ExcelBean {

    /**
     * 反射封装excel表格bean对象
     */
    public static WorkloadAccount elcelWorkloadAccount (List<Object> list) throws Exception{
        //class 文件 不要问为啥不做成 泛型 因为懒
        Class<WorkloadAccount> workloadAccountClass = WorkloadAccount.class;
        WorkloadAccount workloadAccount = new WorkloadAccount();
        //反射拿到反射属性
        Field[] declaredFields = workloadAccountClass.getDeclaredFields();
        //属性数组截取 截掉多余字段
        Field[] fields = Arrays.copyOfRange(declaredFields, 1, declaredFields.length - 1);
        //此代码是保证list数据顺序 与bean字段顺序是一致的
        for (int i = 0; i < list.size(); i++) {
            Field field = fields[i];
            field.setAccessible(true);
            field.set(workloadAccount,list.get(i));
        }
        return workloadAccount;
    }

}
