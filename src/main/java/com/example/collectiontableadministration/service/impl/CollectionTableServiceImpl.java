package com.example.collectiontableadministration.service.impl;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.collectiontableadministration.domain.pojo.*;
import com.example.collectiontableadministration.domain.response.vo.CollectionTableDetailedTextVo;
import com.example.collectiontableadministration.domain.response.vo.CollectionTableVo;
import com.example.collectiontableadministration.mapper.*;
import com.example.collectiontableadministration.service.CollectionTableService;
import com.example.collectiontableadministration.utils.MailUtil;
import com.example.collectiontableadministration.utils.RequestUserIdUtils;
import com.example.collectiontableadministration.utils.TreeUtils;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author 32298
 */
@Service
public class CollectionTableServiceImpl implements CollectionTableService {
    @Autowired
    private CollectionTableMapper collectionTableMapper;

    @Autowired
    private CollectionTableDetailedMapper collectionTableDetailedMapper;
    @Autowired
    private DepartmentMapper departmentMapper;
    @Autowired
    private NumberYearMapper numberYearMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private GroupMapper groupMapper;
    @Autowired
    private RequestUserIdUtils requestUserIdUtils;
    @Autowired
    private UserGroupMapper userGroupMapper;
    @Autowired
    private CollectionTableDetailedTextMapper collectionTableDetailedTextMapper;


    @Override
    public IPage<CollectionTableDetailed> collectionTableList(int pageNum,int pageSize,Long id, Long departmentId, Long yearId, String staticZ, Long collectionTableId) {

        Map<Long, String> dMap = departmentMapper.selectList(null).stream().collect(Collectors.toMap(Department::getId, Department::getDepartmentName));
        Map<Long, String> nMap = numberYearMapper.selectList(null).stream().collect(Collectors.toMap(NumberYear::getId, NumberYear::getNumberYears));
        Map<Long, String> cMap = collectionTableMapper.selectList(null).stream().collect(Collectors.toMap(CollectionTable::getId, CollectionTable::getCollectionTableName));
        Map<Long, String> gMap = groupMapper.selectList(null).stream().collect(Collectors.toMap(Group::getId, Group::getGroupName));

//        MPJLambdaWrapper<CollectionTable> mpjLambdaWrapper = new MPJLambdaWrapper<>();
//        mpjLambdaWrapper.selectAll(CollectionTable.class)
//                        .selectCollection(CollectionTableDetailed.class,CollectionTableVo::getCollectionTableDetailedList)
//                        .leftJoin(CollectionTableDetailed.class,CollectionTableDetailed::getCollectionTableId,CollectionTable::getId)
//                .eq(!ObjectUtils.isEmpty(id),CollectionTable::getId,id).or()
//                .eq(!ObjectUtils.isEmpty(id),CollectionTable::getParentId,id)
//                .eq(!ObjectUtils.isEmpty(departmentId),CollectionTableDetailed::getDepartmentId,departmentId)
//                .eq(!ObjectUtils.isEmpty(yearId),CollectionTableDetailed::getNumberYearId,yearId)
//                .eq(StringUtils.hasLength(staticZ),CollectionTableDetailed::getStaticZ,staticZ)
//                .eq(!ObjectUtils.isEmpty(collectionTableId),CollectionTable::getId,collectionTableId);
//        List<CollectionTableVo> collectionTableVos = collectionTableMapper.selectJoinList(CollectionTableVo.class, mpjLambdaWrapper);
//        collectionTableVos.forEach(collectionTableVo -> {collectionTableVo.getCollectionTableDetailedList()
//                .forEach(collectionTableDetailed ->{
//                    collectionTableDetailed.setDepartmentName(dMap.get(collectionTableDetailed.getDepartmentId()));
//                    collectionTableDetailed.setNumberYearName(nMap.get(collectionTableDetailed.getNumberYearId()));
//                } );});
//        return collectionTableVos;

        IPage<CollectionTableDetailed> collectionTableVoIPage = collectionTableDetailedMapper.selectJoinPage(new Page<>(pageNum, pageSize), CollectionTableDetailed.class
                , new MPJLambdaWrapper<CollectionTableDetailed>().selectAll(CollectionTableDetailed.class)
                        .eq(!ObjectUtils.isEmpty(id), CollectionTableDetailed::getCollectionTableParentId, id)
                        .eq(!ObjectUtils.isEmpty(departmentId), CollectionTableDetailed::getDepartmentId, departmentId)
                        .eq(!ObjectUtils.isEmpty(yearId), CollectionTableDetailed::getNumberYearId, yearId)
                        .eq(StringUtils.hasLength(staticZ), CollectionTableDetailed::getStaticZ, staticZ)
                        .eq(!ObjectUtils.isEmpty(collectionTableId), CollectionTableDetailed::getCollectionTableId, collectionTableId));


        collectionTableVoIPage.getRecords().stream().forEach(collectionTableDetailed -> {
            collectionTableDetailed.setDepartmentName(dMap.get(collectionTableDetailed.getDepartmentId()));
            collectionTableDetailed.setNumberYearName(nMap.get(collectionTableDetailed.getNumberYearId()));
            collectionTableDetailed.setCollectionTableName(cMap.get(collectionTableDetailed.getCollectionTableId()));
            collectionTableDetailed.setGroupName(gMap.get(collectionTableDetailed.getGroupId()));
        });


        return collectionTableVoIPage;

    }

    @Override
    public void saveCollectionTable(CollectionTableDetailed collectionTableDetailed) {
        CollectionTable collectionTable = collectionTableMapper.selectById(collectionTableDetailed.getCollectionTableId());
        collectionTableDetailed.setStaticZ("0");
        collectionTableDetailed.setCollectionTableParentId(collectionTable.getParentId());
        collectionTableDetailedMapper.insert(collectionTableDetailed);
    }

    @Override
    public void delete(Long id) {
        collectionTableDetailedMapper.deleteById(id);
    }

    @Override
    public void update(Long id, String staticZ) {
        CollectionTableDetailed collectionTableDetailed = collectionTableDetailedMapper.selectById(id);
        if("1".equals(staticZ)){
            List<User> users = userMapper.selectList(new LambdaUpdateWrapper<User>().eq(User::getDepartmentId, collectionTableDetailed.getDepartmentId()));
            sendMailFb(users.stream().map(User::getUserEmail).filter(StringUtils::hasLength).collect(Collectors.toList()),collectionTableDetailed);
        }
        collectionTableDetailedMapper.update(new CollectionTableDetailed(),new LambdaUpdateWrapper<CollectionTableDetailed>().set(CollectionTableDetailed::getStaticZ,staticZ).eq(CollectionTableDetailed::getId,id));
    }

    @Override
    public List<CollectionTable> treeListCollection() {
        List<CollectionTable> collectionTables = collectionTableMapper.selectList(null);
        return collectionTables;
    }

    @Override
    public void putGroup(Long id,Long groupId) {
        collectionTableDetailedMapper.update(new CollectionTableDetailed(),new LambdaUpdateWrapper<CollectionTableDetailed>()
                .set(CollectionTableDetailed::getGroupId,groupId).eq(CollectionTableDetailed::getId,id));
    }

    @Override
    public IPage<CollectionTableDetailed> userCollectionTable(int pageNum, int pageSize,Long collectionTableParentId,String staticZ,Long numberYearId) {

        Map<Long, String> dMap = departmentMapper.selectList(null).stream().collect(Collectors.toMap(Department::getId, Department::getDepartmentName));
        Map<Long, String> nMap = numberYearMapper.selectList(null).stream().collect(Collectors.toMap(NumberYear::getId, NumberYear::getNumberYears));
        Map<Long, String> cMap = collectionTableMapper.selectList(null).stream().collect(Collectors.toMap(CollectionTable::getId, CollectionTable::getCollectionTableName));
        Map<Long, String> gMap = groupMapper.selectList(null).stream().collect(Collectors.toMap(Group::getId, Group::getGroupName));

        User user = userMapper.selectById(requestUserIdUtils.getUserId());
        Long id = user.getId();
        Long departmentId = user.getDepartmentId();
        List<Long> collect = userGroupMapper.selectList(new LambdaUpdateWrapper<UserGroup>().eq(UserGroup::getUserId, id)).stream().map(UserGroup::getGroupId).collect(Collectors.toList());
        LambdaQueryWrapper<CollectionTableDetailed> lambdaQueryWrapper = new LambdaQueryWrapper<CollectionTableDetailed>();
                lambdaQueryWrapper.and(wrapper->wrapper.eq(user.getRoleId()!=1,CollectionTableDetailed::getDepartmentId,departmentId)
                                .or()
                                .in(collect.size()!=0&&id!=1,CollectionTableDetailed::getGroupId,collect))
                .eq(!ObjectUtils.isEmpty(collectionTableParentId),CollectionTableDetailed::getCollectionTableParentId,collectionTableParentId)
                .eq(StringUtils.hasLength(staticZ),CollectionTableDetailed::getStaticZ,staticZ)
                .eq(!ObjectUtils.isEmpty(numberYearId), CollectionTableDetailed::getNumberYearId,numberYearId);

        Page<CollectionTableDetailed> collectionTableDetailedPage = collectionTableDetailedMapper.selectPage(new Page<>(pageNum, pageSize), lambdaQueryWrapper);


        collectionTableDetailedPage.getRecords().stream().forEach(collectionTableDetailed -> {
            collectionTableDetailed.setDepartmentName(dMap.get(collectionTableDetailed.getDepartmentId()));
            collectionTableDetailed.setNumberYearName(nMap.get(collectionTableDetailed.getNumberYearId()));
            collectionTableDetailed.setCollectionTableName(cMap.get(collectionTableDetailed.getCollectionTableId()));
            collectionTableDetailed.setGroupName(gMap.get(collectionTableDetailed.getGroupId()));
        });

        return collectionTableDetailedPage;
    }

    @Override
    public void saveUserCollectionTable(CollectionTableDetailedText collectionTableDetailedText) {
       collectionTableDetailedText.setUserId(requestUserIdUtils.getUserId());
        collectionTableDetailedText.setCollectionTableDetailedTextJson(JSON.toJSONString(collectionTableDetailedText.getCollectionTableDetailedExcel()));
        collectionTableDetailedTextMapper.insert(collectionTableDetailedText);
    }

    @Override
    public List<CollectionTableDetailedTextVo> getUserByCollectionTableDetailedTextList(Long collectionTableDetailedId) {
        Map<Long, String> cMap = collectionTableMapper.selectList(null).stream().collect(Collectors.toMap(CollectionTable::getId, CollectionTable::getCollectionTableName));
        List<CollectionTableDetailedTextVo> collectionTableDetailedTextVos = collectionTableDetailedTextMapper.selectJoinList(CollectionTableDetailedTextVo.class, new MPJLambdaWrapper<CollectionTableDetailedText>()
                .select(CollectionTableDetailedText::getId)
                .select(CollectionTableDetailedText::getCollectionTableDetailedTextJson)
                .select(CollectionTableDetailed::getCollectionTableId)
                .select(CollectionTableDetailed::getCollectionTableParentId)
                        .select(CollectionTableDetailedText::isApplyFor)
                .leftJoin(CollectionTableDetailed.class, CollectionTableDetailed::getId, CollectionTableDetailedText::getCollectionTableDetailedId)
                .eq(CollectionTableDetailedText::getUserId, requestUserIdUtils.getUserId())
                .eq(CollectionTableDetailedText::getCollectionTableDetailedId,collectionTableDetailedId));

        collectionTableDetailedTextVos.forEach(collectionTableDetailedTextVo -> {
            collectionTableDetailedTextVo.setCollectionTableName(cMap.get(collectionTableDetailedTextVo.getCollectionTableId()));
            collectionTableDetailedTextVo.setCollectionTableParentName(cMap.get(collectionTableDetailedTextVo.getCollectionTableParentId()));
            collectionTableDetailedTextVo.setCollectionTableDetailedExcel(JSON.parseObject(collectionTableDetailedTextVo.getCollectionTableDetailedTextJson()
                    ,CollectionTableDetailedExcel.class));
        });

        return collectionTableDetailedTextVos;
    }

    @Override
    public void updateCollectionTableDetailedTextList(CollectionTableDetailedText collectionTableDetailedText) {
        Long roleId = userMapper.selectById(requestUserIdUtils.getUserId()).getRoleId();
        if(!collectionTableDetailedTextMapper.selectById(collectionTableDetailedText.getId()).isApplyFor()&&(roleId!=1&&roleId!=2)){
           throw new RuntimeException("当前收集表需要向管理员申请后 才能修改 亲 别问为什么 我叫杨子涵 我就要这个加 你能怎么招 不行别干啦 世界那么大 你得去看看");
        }

        collectionTableDetailedText.setCollectionTableDetailedTextJson(JSON.toJSONString(collectionTableDetailedText.getCollectionTableDetailedExcel()));
        collectionTableDetailedTextMapper.update(new CollectionTableDetailedText(),new LambdaUpdateWrapper<CollectionTableDetailedText>()
                .set(CollectionTableDetailedText::getCollectionTableDetailedTextJson,collectionTableDetailedText.getCollectionTableDetailedTextJson())
                .set(CollectionTableDetailedText::isApplyFor,false)
                .eq(CollectionTableDetailedText::getId,collectionTableDetailedText.getId()));
    }

    @Override
    public void userApplyForUpdateCollectionTable(Long id) {

        String msg = "用户"+userMapper.selectById(requestUserIdUtils.getUserId()).getUserName()+"需要修改收集表"+
                "同意点击链接 : <a href='"+"http://43.143.214.146:8001/collectionTableService/collectionTable/userApplyForUpdateCollectionTableAdmin?userId="+requestUserIdUtils.getUserId()
                +"&id="+id+"&flag="+true+"'>同意</a>"+
                "不同意点击链接 : <a href='"+"http://43.143.214.146:8001/collectionTableService/collectionTable/userApplyForUpdateCollectionTableAdmin?userId="+requestUserIdUtils.getUserId()
                +"&id="+id+"&flag="+false+"'>不同意</a>";


        userMapper.selectList(new LambdaUpdateWrapper<User>()
                .eq(User::getRoleId, 1)).stream().map(User::getUserEmail).filter(StringUtils::hasLength).forEach(
                        mail -> {
                            try {
                                MailUtil.sendMail(mail,"收集表申请信息",msg);
                            } catch (MessagingException e) {
                                throw new RuntimeException(e);
                            }
                        }
        );



    }

    @Override
    public void userApplyForUpdateCollectionTableAdmin(Long userId, Long id,boolean flag) {
        User user = userMapper.selectById(userId);
        if(!StringUtils.hasLength(user.getUserEmail())){
            return;
        }
        if(flag){
            try {
                MailUtil.sendMail(user.getUserEmail(),"收集表修改回执","您的收集表修改请求已同意");
                collectionTableDetailedTextMapper.update(new CollectionTableDetailedText()
                        ,new LambdaUpdateWrapper<CollectionTableDetailedText>().set(CollectionTableDetailedText::isApplyFor,true).eq(CollectionTableDetailedText::getId,id));

            } catch (MessagingException e) {
                throw new RuntimeException(e);
            }
        }else {
            try {
                MailUtil.sendMail(user.getUserEmail(),"收集表修改回执","您的收集表修改请求已驳回");
            } catch (MessagingException e) {
                throw new RuntimeException(e);
            }
        }

    }

    @Override
    public void deleteCollectionTableDetailedText(Long id) {
        collectionTableDetailedTextMapper.deleteById(id);
    }

    @Override
    public IPage<CollectionTableDetailedTextVo> applyForUpdateCollectionTable(int pageNum,int pageSize,Long numberYearId, Long departmentId, Long userId, Long collectionTableParentId,Long collectionTableId) {
        Map<Long, String> cMap = collectionTableMapper.selectList(null).stream().collect(Collectors.toMap(CollectionTable::getId, CollectionTable::getCollectionTableName));
        IPage<CollectionTableDetailedTextVo> collectionTableDetailedTextVoIPage = collectionTableDetailedTextMapper.selectJoinPage(new Page<>(pageNum, pageSize), CollectionTableDetailedTextVo.class
                , new MPJLambdaWrapper<CollectionTableDetailedText>()
                        .select(CollectionTableDetailedText::getId)
                        .select(CollectionTableDetailedText::getCollectionTableDetailedTextJson)
                        .select(CollectionTableDetailed::getCollectionTableId)
                        .select(CollectionTableDetailed::getCollectionTableParentId)
                        .select(CollectionTableDetailedText::isApplyFor)
                        .select(CollectionTableDetailed::getDepartmentId)
                        .select(CollectionTableDetailedText::getUserId)
                        .select(CollectionTableDetailed::getNumberYearId)
                        .leftJoin(CollectionTableDetailed.class, CollectionTableDetailed::getId, CollectionTableDetailedText::getCollectionTableDetailedId)
                        .eq(!ObjectUtils.isEmpty(numberYearId), CollectionTableDetailed::getNumberYearId, numberYearId)
                        .eq(!ObjectUtils.isEmpty(departmentId), CollectionTableDetailed::getDepartmentId, departmentId)
                        .eq(!ObjectUtils.isEmpty(userId), CollectionTableDetailedText::getUserId, userId)
                        .eq(!ObjectUtils.isEmpty(collectionTableId), CollectionTableDetailed::getCollectionTableId, collectionTableId)
                        .eq(!ObjectUtils.isEmpty(collectionTableParentId),CollectionTableDetailed::getCollectionTableParentId,collectionTableParentId));

        collectionTableDetailedTextVoIPage.getRecords().forEach(collectionTableDetailedTextVo -> {
            collectionTableDetailedTextVo.setCollectionTableName(cMap.get(collectionTableDetailedTextVo.getCollectionTableId()));
            collectionTableDetailedTextVo.setCollectionTableParentName(cMap.get(collectionTableDetailedTextVo.getCollectionTableParentId()));
            collectionTableDetailedTextVo.setCollectionTableDetailedExcel(JSON.parseObject(collectionTableDetailedTextVo.getCollectionTableDetailedTextJson()
                    ,CollectionTableDetailedExcel.class));
        });
        return collectionTableDetailedTextVoIPage;
    }

    @Async
    public void  sendMailFb(List<String> mailAccount,CollectionTableDetailed collectionTableDetailed){
        String msg = "管理员在"+new Date()+"发布啦收集表,截止时间"+collectionTableDetailed.getExpirationTime()+"w望周知";
        mailAccount.forEach(
                e-> {
                    try {
                        MailUtil.sendMail(e,"发布",msg);
                    } catch (MessagingException ex) {
                        throw new RuntimeException(ex);
                    }
                }
        );
    }
}
