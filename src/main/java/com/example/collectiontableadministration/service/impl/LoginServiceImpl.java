package com.example.collectiontableadministration.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.example.collectiontableadministration.domain.comm.StaticConstant;
import com.example.collectiontableadministration.domain.pojo.Role;
import com.example.collectiontableadministration.domain.pojo.User;
import com.example.collectiontableadministration.domain.response.vo.UserAndRole;
import com.example.collectiontableadministration.mapper.RoleMapper;
import com.example.collectiontableadministration.mapper.UserMapper;
import com.example.collectiontableadministration.service.LoginService;
import com.example.collectiontableadministration.utils.AesUtil;
import com.example.collectiontableadministration.utils.JwtUtils;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;

/**
 * @author 杨子涵
 * 登录逻辑层实现类
 */
@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Override
    public String login(User user) throws Exception {
        //构建条件
        MPJLambdaWrapper<User> wrapper = new MPJLambdaWrapper<User>()
                .selectAll(User.class)
                .select(Role::getRoleName)
                .leftJoin(Role.class, Role::getId, User::getRoleId)
                .eq(User::getUserAccount,user.getUserAccount())

                .eq(User::getPassWord, AesUtil.encrypt(user.getPassWord(), StaticConstant.AES_KEY));
        //获取登录对象
        UserAndRole userAndRole = userMapper.selectJoinOne(UserAndRole.class, wrapper);
        //判断查询是否成功
        Assert.isTrue(ObjectUtil.isNotEmpty(userAndRole),()->{throw new RuntimeException("用户名或密码错误");});
        //调用jwt
        return JwtUtils.geneJsonWebToken(userAndRole);
    }
}
