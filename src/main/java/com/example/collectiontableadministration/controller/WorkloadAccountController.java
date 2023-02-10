package com.example.collectiontableadministration.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.collectiontableadministration.domain.dto.WorkLoadPage;
import com.example.collectiontableadministration.domain.pojo.NumberYear;
import com.example.collectiontableadministration.domain.response.vo.WorkLadCorrelationVo;
import com.example.collectiontableadministration.service.WorkloadService;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * @author lhy
 */
@RestController
@RequestMapping("/workloadService/workload")
public class WorkloadAccountController {

    @Autowired
    private WorkloadService workloadService;

    /**
     * 1.导入工作量表接口
     * 2.分页查询工作量表接口
     * 3.删除工作量表接口
     */

    @PostMapping("/importWorkload")
    public void importWorkload(@RequestParam("file") MultipartFile file,String xId,String yId) throws IOException, IllegalAccessException {
       workloadService.importWorkload(file,xId,yId);
    }

    @PostMapping("/workloadList")
    public IPage<WorkLadCorrelationVo> workloadList(@RequestBody WorkLoadPage workLoadPage){
       return workloadService.workloadList(workLoadPage);
    }
    @DeleteMapping("/deleteworkload")
    public void deleteWorkload(@RequestParam Long id){
        workloadService.deleteWorkload(id);
    }
    @DeleteMapping("deleteworkloadList")
    public void deleteWorkloadList(Long xId,Long yId){
        workloadService.deleteWorkloadList(xId,yId);
    }
    @GetMapping("/getYearList")
    public List<NumberYear> getYearList(){
        return workloadService.getYearList();
    }
}
