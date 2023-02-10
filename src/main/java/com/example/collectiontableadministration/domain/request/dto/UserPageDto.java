package com.example.collectiontableadministration.domain.request.dto;


import cn.hutool.db.Page;
import com.example.collectiontableadministration.domain.request.PageBean;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author 杨子涵
 * 用户查询dto
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UserPageDto extends PageBean {
    private String userAccount;
    private String userName;
}
