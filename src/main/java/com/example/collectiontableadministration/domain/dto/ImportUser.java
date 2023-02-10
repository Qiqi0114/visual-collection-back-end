package com.example.collectiontableadministration.domain.dto;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

/**
 * @author yzh
 */
@Data
public class ImportUser {
    @Excel(name = "姓名", orderNum = "0")
    private  String userName;
    @Excel(name = "性别", orderNum = "1")
    private String  userSex;
    @Excel(name = "邮箱", orderNum = "3")
    private String userMail;
    @Excel(name = "电话号", orderNum = "2")
    private String userPhone;
    @Excel(name = "系", orderNum = "5")
    private String department;

}
