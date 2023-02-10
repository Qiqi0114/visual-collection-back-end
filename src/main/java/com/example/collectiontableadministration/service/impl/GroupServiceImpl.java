package com.example.collectiontableadministration.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.collectiontableadministration.domain.pojo.Group;
import com.example.collectiontableadministration.domain.pojo.User;
import com.example.collectiontableadministration.domain.pojo.UserGroup;
import com.example.collectiontableadministration.domain.request.dto.UserGroupDto;
import com.example.collectiontableadministration.mapper.GroupMapper;
import com.example.collectiontableadministration.mapper.UserGroupMapper;
import com.example.collectiontableadministration.service.GroupService;
import com.example.collectiontableadministration.service.UserService;
import org.apache.poi.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GroupServiceImpl implements GroupService {
    @Autowired
    private GroupMapper groupMapper;

    @Autowired
    private UserGroupMapper userGroupMapper;
    @Autowired
    private UserService userService;

    @Override
    public void saveGroup(Group group) {
        groupMapper.insert(group);
    }

    @Override
    public void saveUserGroup(UserGroupDto userGroupDto) {
       userGroupDto.getUserId().forEach(
               id->{
                   UserGroup userGroup = new UserGroup();
                   userGroup.setUserId(id);
                   userGroup.setGroupId(userGroupDto.getId());
                   userGroupMapper.insert(userGroup);
               }
       );
    }

    @Override
    public void deleteUserGroup(UserGroupDto userGroupDto) {
        userGroupDto.getUserId().forEach(
                id->{
                    UserGroup userGroup = new UserGroup();
                    userGroup.setUserId(id);
                    userGroup.setGroupId(userGroupDto.getId());
                    userGroupMapper.delete(new LambdaUpdateWrapper<UserGroup>().eq(UserGroup::getUserId,id).eq(UserGroup::getGroupId,userGroupDto.getId()));
                }
        );
    }

    @Override
    public IPage<Group> getGroupListPage(int pageNum, int pageSize, String groupName) {
        return groupMapper.selectPage(new Page<>(pageNum,pageSize),new LambdaQueryWrapper<Group>().like(StringUtils.hasLength(groupName),Group::getGroupName,groupName));
    }

    @Override
    public List<User> getGroupByList(Long id) {
        return userService.getBaseMapper().selectBatchIds(userGroupMapper.selectList(new LambdaUpdateWrapper<UserGroup>()
                .eq(UserGroup::getGroupId,id)).stream().map(UserGroup::getUserId).collect(Collectors.toList()));
    }
}
