package com.example.collectiontableadministration.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.collectiontableadministration.domain.UserDepartmentVo;
import com.example.collectiontableadministration.domain.comm.StaticConstant;
import com.example.collectiontableadministration.domain.dto.ImportUser;
import com.example.collectiontableadministration.domain.dto.UserPage;
import com.example.collectiontableadministration.domain.pojo.Department;
import com.example.collectiontableadministration.domain.pojo.Role;
import com.example.collectiontableadministration.domain.pojo.User;
import com.example.collectiontableadministration.domain.pojo.WorkloadAccount;
import com.example.collectiontableadministration.domain.request.dto.UserPageDto;
import com.example.collectiontableadministration.domain.response.vo.UserAndRole;
import com.example.collectiontableadministration.domain.response.vo.WorkLadAccountExcel;
import com.example.collectiontableadministration.mapper.DepartmentMapper;
import com.example.collectiontableadministration.mapper.UserMapper;
import com.example.collectiontableadministration.service.DepartmentService;
import com.example.collectiontableadministration.service.UserService;
import com.example.collectiontableadministration.utils.AesUtil;
import com.example.collectiontableadministration.utils.MailUtil;
import com.example.collectiontableadministration.utils.PinYinUtil;
import com.github.yulichang.base.MPJBaseServiceImpl;
import com.github.yulichang.query.MPJQueryWrapper;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author ?????????
 */
@Service
public class UserServiceImpl extends MPJBaseServiceImpl<UserMapper,User> implements UserService {

    @Autowired
    private DepartmentMapper departmentMapper;
    @Autowired
    private UserMapper userMapper;



    @Override
    public void importUserExcel(List<ImportUser> users) {
        Map<String, Long> dMap = departmentMapper.selectList(null).stream().collect(Collectors.toMap(Department::getDepartmentName, Department::getId));
        List<User> listUser = list();
        //??????????????????
        List<User> list = new ArrayList<>();
        users.forEach(importUser->{
            if(listUser.stream().map(User::getUserName).noneMatch(name->name.equals(importUser.getUserName()))){
                try {
                    list.add(new User(importUser.getUserName(), PinYinUtil.getString(importUser.getUserName())
                    ,AesUtil.encrypt("123456", StaticConstant.AES_KEY)
                    ,"???".equals(importUser.getUserSex())?1:0
                    ,importUser.getUserMail()
                    ,importUser.getUserPhone()
                    ,dMap.get(importUser.getDepartment())
                    ,3L));
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
       saveBatch(list);
    }

    @Override
    public IPage<UserDepartmentVo> getUserPageList(UserPage userPage) {
        MPJLambdaWrapper<User> wrapper = new MPJLambdaWrapper<User>()
                .selectAll(User.class)
                //????????????
                .select(Department::getDepartmentName)
                .select(Role::getRoleName)
                .leftJoin(Department.class, Department::getId, User::getDepartmentId)
                .leftJoin(Role.class,Role::getId,User::getRoleId)
                .like(StringUtils.hasLength(userPage.getUserName()),User::getUserName,userPage.getUserName());


        //???????????? ??????????????? mybatis plus ???????????????
        System.out.println(wrapper.getLogicSql());
       return userMapper.selectJoinPage(new Page<>(userPage.getPageNum(), userPage.getPageSize()), UserDepartmentVo.class, wrapper);
    }

    @Override
    public void putUser(User user) {
        updateById(user);
    }

    @Override
    public void innerUser(User user) {
        user.setRoleId(3L);
        save(user);
    }

    @Override
    public List<Department> departmentList() {
        return departmentMapper.selectList(null);
    }

    @Override
    public void passWordCz(String id) throws Exception {
        User user = userMapper.selectById(id);
        if(!StringUtils.hasLength(user.getUserEmail())){
            throw new RuntimeException("???????????????????????????");
        }
        MailUtil.sendMail(user.getUserEmail(),"????????????",user.getUserName()+"??????????????????????????????????????????????????????123456,????????????");
        update(new User(),new LambdaUpdateWrapper<User>().set(User::getPassWord,AesUtil.encrypt("123456", StaticConstant.AES_KEY)).eq(User::getId,id));
    }

    @Override
    public User getUserById(String id) throws MessagingException {
//        MPJLambdaWrapper<User> wrapper = new MPJLambdaWrapper<User>()
//                .selectAll(User.class)
//                //????????????
//                .select(Department::getDepartmentName)
//                .leftJoin(Department.class, Department::getId, User::getDepartmentId)
//                .eq(User::getId,id);
//        return userMapper.selectJoinOne(UserDepartmentVo.class,wrapper);
        return userMapper.selectById(id);
    }

    @Override
    public void deleteUser(String id) {
        userMapper.deleteById(id);
    }

}
