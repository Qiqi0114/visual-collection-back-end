package com.example.collectiontableadministration.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.collectiontableadministration.domain.pojo.Group;
import com.example.collectiontableadministration.domain.pojo.User;
import com.example.collectiontableadministration.domain.request.dto.UserGroupDto;

import java.util.List;

public interface GroupService {
    void saveGroup(Group group);

    void saveUserGroup(UserGroupDto userGroupDto);

    void deleteUserGroup(UserGroupDto userGroupDto);

    IPage<Group> getGroupListPage(int pageNum, int pageSize, String groupName);

    List<User> getGroupByList(Long id);
}
