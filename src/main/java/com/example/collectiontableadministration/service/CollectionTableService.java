package com.example.collectiontableadministration.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.collectiontableadministration.domain.pojo.CollectionTable;
import com.example.collectiontableadministration.domain.pojo.CollectionTableDetailed;
import com.example.collectiontableadministration.domain.pojo.CollectionTableDetailedText;
import com.example.collectiontableadministration.domain.response.vo.CollectionTableDetailedTextVo;
import com.example.collectiontableadministration.domain.response.vo.CollectionTableVo;

import java.util.List;

public interface CollectionTableService {

    IPage<CollectionTableDetailed> collectionTableList(int pageNum,int pageSize,Long id, Long departmentId, Long yearId, String staticZ, Long collectionTableId);

    void saveCollectionTable(CollectionTableDetailed collectionTableDetailed);

    void delete(Long id);

    void update(Long id, String staticZ);

    List<CollectionTable> treeListCollection();

    void putGroup(Long id,Long groupId);

    IPage<CollectionTableDetailed> userCollectionTable(int pageNum, int pageSize,Long collectionTableParentId,String staticZ,Long numberYearId);

    void saveUserCollectionTable(CollectionTableDetailedText collectionTableDetailedText);

    List<CollectionTableDetailedTextVo>  getUserByCollectionTableDetailedTextList(Long collectionTableDetailedId);

    void updateCollectionTableDetailedTextList(CollectionTableDetailedText collectionTableDetailedText);

    void userApplyForUpdateCollectionTable(Long id);

    void userApplyForUpdateCollectionTableAdmin(Long userId,Long id,boolean flag);

    void deleteCollectionTableDetailedText(Long id);

    IPage<CollectionTableDetailedTextVo> applyForUpdateCollectionTable(int pageNum,int pageSize,Long numberYearId, Long departmentId, Long userId, Long collectionTableParentId,Long collectionTableId);

}
