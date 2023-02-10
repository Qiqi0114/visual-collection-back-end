package com.example.collectiontableadministration.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.collectiontableadministration.domain.dto.WorkLoadPage;
import com.example.collectiontableadministration.domain.pojo.NumberYear;
import com.example.collectiontableadministration.domain.pojo.User;
import com.example.collectiontableadministration.domain.pojo.Workload;
import com.example.collectiontableadministration.domain.response.vo.WorkLadCorrelationVo;
import com.github.yulichang.base.MPJBaseService;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * @author 32298
 */
public interface WorkloadService extends MPJBaseService<Workload> {
    /**
     * 导入文件
     * @param file
     */
    void importWorkload(MultipartFile file,String xId,String yId) throws IOException, IllegalAccessException;


    IPage<WorkLadCorrelationVo> workloadList(WorkLoadPage workLoadPage);

    void deleteWorkload(Long id);

    void deleteWorkloadList(Long xId,Long yId);

    List<NumberYear> getYearList();

}
