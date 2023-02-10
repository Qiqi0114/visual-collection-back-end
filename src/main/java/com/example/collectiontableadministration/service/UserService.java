package com.example.collectiontableadministration.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.collectiontableadministration.domain.UserDepartmentVo;
import com.example.collectiontableadministration.domain.dto.ImportUser;
import com.example.collectiontableadministration.domain.dto.UserPage;
import com.example.collectiontableadministration.domain.pojo.Department;
import com.example.collectiontableadministration.domain.pojo.User;
import com.example.collectiontableadministration.domain.request.dto.UserPageDto;
import com.example.collectiontableadministration.domain.response.vo.UserAndRole;
import com.example.collectiontableadministration.domain.response.vo.WorkLadAccountExcel;
import com.github.yulichang.base.MPJBaseService;

import javax.mail.MessagingException;
import java.util.List;

/**
 * @author 杨子涵
 * 用户持久层
 */
public interface UserService extends MPJBaseService<User> {

    void importUserExcel(List<ImportUser> users);

    IPage<UserDepartmentVo> getUserPageList(UserPage userPage);

    void putUser(User user);

    void innerUser(User user);

    List<Department> departmentList();

    void passWordCz(String id) throws Exception;

    User getUserById(String id) throws MessagingException;

    void deleteUser(String id);

}
