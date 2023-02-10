package com.example.collectiontableadministration.service.impl;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.collectiontableadministration.domain.UserDepartmentVo;
import com.example.collectiontableadministration.domain.dto.WorkLoadPage;
import com.example.collectiontableadministration.domain.pojo.*;
import com.example.collectiontableadministration.domain.response.vo.WorkLadCorrelationVo;
import com.example.collectiontableadministration.mapper.NumberYearMapper;
import com.example.collectiontableadministration.mapper.UserMapper;
import com.example.collectiontableadministration.mapper.WorkloadCorrelationMapper;
import com.example.collectiontableadministration.mapper.WorkloadMapper;
import com.example.collectiontableadministration.service.UserService;
import com.example.collectiontableadministration.service.WorkloadService;
import com.example.collectiontableadministration.utils.RequestUserIdUtils;
import com.github.yulichang.base.MPJBaseServiceImpl;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.lang.reflect.Field;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author 32298
 */
@Service
public class WorkloadServiceImpl extends MPJBaseServiceImpl<WorkloadMapper,Workload> implements WorkloadService{
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private WorkloadCorrelationMapper workloadCorrelationMapper;
    @Autowired
    private WorkloadMapper workloadMapper;
    @Autowired
    private RequestUserIdUtils requestUserIdUtils;



    @Autowired
    private NumberYearMapper numberYearMapper;
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void importWorkload(MultipartFile file,String xId,String yId) throws IOException, IllegalAccessException {

        XSSFWorkbook wb=new XSSFWorkbook(file.getInputStream());
        /**
         * 校验 1.校验当前导入人在库中是否存在
         */
        List<User> users = userMapper.selectList(null);

        //定义重复名称集合
        List<String> repeatList = new ArrayList<>();
        List<Workload> workloadList = new ArrayList<>();
        for (int i = 0; i < wb.getNumberOfSheets(); i++) {
            XSSFSheet sheetAt = wb.getSheetAt(i);
            Map<String, Long> collect = users.stream().collect(Collectors.toMap(User::getUserName, User::getId));
            List<String> userNameList = users.stream().map(User::getUserName).collect(Collectors.toList());

            WorkloadCorrelation workloadCorrelation = new WorkloadCorrelation();
            workloadCorrelation.setUserId(collect.get(sheetAt.getSheetName()));
            workloadCorrelation.setDepartmentId(Long.parseLong(xId));
            workloadCorrelation.setNumberYearId(Long.parseLong(yId));
            if(ObjectUtil.isNotEmpty(workloadCorrelationMapper.selectJoinOne(WorkloadCorrelation.class,new MPJLambdaWrapper<WorkloadCorrelation>().eq(WorkloadCorrelation::getUserId,collect.get(sheetAt.getSheetName()))
                    .eq(WorkloadCorrelation::getDepartmentId,Long.parseLong(xId)).eq(WorkloadCorrelation::getNumberYearId,Long.parseLong(yId))))){
                throw new RemoteException("这个人 "+""+" 在当前年限下 当前系下 以已经存在了");
            }
            workloadCorrelationMapper.insert(workloadCorrelation);
            if(userNameList.stream().noneMatch(e->e.equals(sheetAt.getSheetName()))){
                repeatList.add(sheetAt.getSheetName());
            }
            for (int j = 4; j <= sheetAt.getLastRowNum(); j++) {
                XSSFRow row = sheetAt.getRow(j);
                XSSFCell cellPd = row.getCell(13);
                cellPd.setCellType(CellType.STRING);
                if(!StringUtils.hasLength(cellPd.getStringCellValue())){
                    continue;
                 }
                 Workload workload = excelBeanImport(row, new Workload());
                 workload.setWorkloadCorrelationId(workloadCorrelation.getId());
                 workloadList.add(workload);
                if("合计".equals(row.getCell(12).getStringCellValue())){
                    XSSFCell cell13 = row.getCell(13);
                    cell13.setCellType(CellType.STRING);
                    XSSFCell cell15 = row.getCell(15);
                    cell15.setCellType(CellType.STRING);
                    XSSFCell cell16 = row.getCell(16);
                    cell16.setCellType(CellType.STRING);
                    XSSFCell cell22 = row.getCell(22);
                    cell22.setCellType(CellType.STRING);
                    XSSFCell cell23 = row.getCell(23);
                    cell23.setCellType(CellType.STRING);
                    workloadCorrelation.setF(cell13.getStringCellValue());
                    workloadCorrelation.setY1(cell15.getStringCellValue());
                    workloadCorrelation.setY2(cell16.getStringCellValue());
                    workloadCorrelation.setS1(cell22.getStringCellValue());
                    workloadCorrelation.setZ(cell23.getStringCellValue());
                    workloadCorrelationMapper.updateById(workloadCorrelation);
                    break;
                }
             }

        }

        workloadList.forEach(e->{workloadMapper.insert(e);});
        if(repeatList.size()!=0){
            throw new RuntimeException("当前导入人在库中不存在"+repeatList.toString());
        }
    }

    @Override
    public IPage<WorkLadCorrelationVo> workloadList(WorkLoadPage workLoadPage) {

        Long userId = requestUserIdUtils.getUserId();
        User user = userMapper.selectById(userId);
        MPJLambdaWrapper<WorkloadCorrelation> lambdaQueryWrapper = new MPJLambdaWrapper<>();
        lambdaQueryWrapper.selectAll(WorkloadCorrelation.class)
                         .select(User::getUserName)
                         .select(User::getId)
                         .select(User::getRoleId)
                         .select(Department::getDepartmentName)
                         .select(NumberYear::getNumberYears)
                                 .leftJoin(User.class,User::getId,WorkloadCorrelation::getUserId)
                                         .leftJoin(Department.class,Department::getId,WorkloadCorrelation::getDepartmentId)
                                                  .leftJoin(NumberYear.class,NumberYear::getId,WorkloadCorrelation::getNumberYearId)
                                                         .eq(StringUtils.hasLength(workLoadPage.getUserName()),User::getUserName,workLoadPage.getUserName())
                                                                 .eq(ObjectUtil.isNotEmpty(workLoadPage.getDepartmentId()),Department::getId,workLoadPage.getDepartmentId())
                                                                         .eq(ObjectUtil.isNotEmpty(workLoadPage.getYearId()),NumberYear::getId,workLoadPage.getYearId())
                .eq(user.getRoleId()!=1,User::getId,userId);


        IPage<WorkLadCorrelationVo> workLadCorrelationVoIpage = workloadCorrelationMapper.selectJoinPage(new Page<>(workLoadPage.getPageNum(), workLoadPage.getPageSize()), WorkLadCorrelationVo.class, lambdaQueryWrapper);
        workLadCorrelationVoIpage.getRecords().forEach(e->{
            e.setWorkloadList(workloadMapper.selectList(new LambdaQueryWrapper<Workload>().eq(Workload::getWorkloadCorrelationId,e.getId())));
        });
        return workLadCorrelationVoIpage;

    }

    @Override
    public void deleteWorkload(Long id) {
        workloadMapper.delete(new LambdaQueryWrapper<Workload>().eq(Workload::getWorkloadCorrelationId,id));
        workloadCorrelationMapper.delete(new LambdaQueryWrapper<WorkloadCorrelation>().eq(WorkloadCorrelation::getId,id));
    }

    @Override
    public void deleteWorkloadList(Long xId,Long yId) {
        List<WorkloadCorrelation> workloadCorrelationList = workloadCorrelationMapper.selectList(new LambdaQueryWrapper<WorkloadCorrelation>().eq(WorkloadCorrelation::getNumberYearId,yId).eq(WorkloadCorrelation::getDepartmentId,xId));
        List<Long> collect = workloadCorrelationList.stream().map(WorkloadCorrelation::getId).collect(Collectors.toList());
        workloadCorrelationMapper.delete(new LambdaQueryWrapper<WorkloadCorrelation>().eq(WorkloadCorrelation::getDepartmentId,xId).eq(WorkloadCorrelation::getNumberYearId,yId));
        collect.forEach(
                e->{
                    workloadMapper.delete(new LambdaQueryWrapper<Workload>().eq(Workload::getWorkloadCorrelationId,e));
                }
        );

    }

    @Override
    public List<NumberYear> getYearList() {
        return numberYearMapper.selectList(null);
    }


    public  Workload excelBeanImport(XSSFRow row, Workload workload) throws IllegalAccessException {
        Class<? extends Workload> aClass = workload.getClass();
        Field[] declaredFields = aClass.getDeclaredFields();
        for (int i = 1; i < 24; i++) {
            declaredFields[i].setAccessible(true);
            XSSFCell cell = row.getCell(i-1);
            cell.setCellType(CellType.STRING);
            declaredFields[i].set(workload,cell.getStringCellValue());
        }
        return workload;
    }

}
