package com.example.collectiontableadministration.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.collectiontableadministration.domain.pojo.Group;
import com.example.collectiontableadministration.domain.pojo.User;
import com.example.collectiontableadministration.domain.request.dto.UserGroupDto;
import com.example.collectiontableadministration.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.swing.*;
import java.util.List;

/**
 * @author 用户组管理
 */
@RestController
@RequestMapping("/UserGroupService/UserGroup")
public class UserGroupController {
    @Autowired
    private GroupService groupService;

    /**
     * 创建用户组
     * 用户组添加人
     * 用户组删除人
     * 删除用户组
     * 用户组分页条件查询
     * 用户组下的人
     */

    @PostMapping("/saveGroup")
    public void saveGroup(@RequestBody Group group){
         groupService.saveGroup(group);
    }

    @PostMapping("/saveUserGroup")
    public void saveUserGroup(@RequestBody UserGroupDto userGroupDto){
        groupService.saveUserGroup(userGroupDto);
    }

    @DeleteMapping("/deleteUserGroup")
    public void deleteUserGroup(@RequestBody UserGroupDto userGroupDto){
        groupService.deleteUserGroup(userGroupDto);
    }
    @GetMapping("/getGroupListPage")
    public IPage<Group> getGroupListPage(int pageNum,int pageSize,String groupName){
      return  groupService.getGroupListPage(pageNum,pageSize,groupName);
    }
    @GetMapping("/getGroupByList")
    public List<User>  getGroupByList(@RequestParam("id") Long id){
       return groupService.getGroupByList(id);
    }

    /***-------------------------------------------------------------------------------------------------**/



}
