package com.example.collectiontableadministration.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.collectiontableadministration.domain.pojo.CollectionTable;
import com.example.collectiontableadministration.domain.pojo.CollectionTableDetailed;
import com.example.collectiontableadministration.domain.pojo.CollectionTableDetailedText;
import com.example.collectiontableadministration.domain.response.vo.CollectionTableDetailedTextVo;
import com.example.collectiontableadministration.domain.response.vo.CollectionTableVo;
import com.example.collectiontableadministration.service.CollectionTableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.function.LongFunction;

/**
 * @author
 */
@RestController
@RequestMapping("/collectionTableService/collectionTable")
public class CollectionTableController {
    @Autowired
    private CollectionTableService collectionTableService;

    @GetMapping("/collectionTableList")
    public IPage<CollectionTableDetailed> collectionTableList(int pageNum,int pageSize,Long id, Long departmentId, Long yearId, String staticZ, Long collectionTableId){
      return collectionTableService.collectionTableList(pageNum,pageSize,id,departmentId,yearId,staticZ,collectionTableId);
    }

    @PostMapping("/saveCollectionTable")
    private void saveCollectionTable(@RequestBody CollectionTableDetailed collectionTableDetailed){
          collectionTableService.saveCollectionTable(collectionTableDetailed);
    }
    @DeleteMapping()
    private void  delete(@RequestParam("id") Long id){
        collectionTableService.delete(id);
    }
    @PutMapping()
    private void update(@RequestParam("id") Long id,@RequestParam("staticZ") String staticZ){
        collectionTableService.update(id,staticZ);
    }
    @GetMapping("/treeListCollection")
    private List<CollectionTable> treeListCollection(){
      return collectionTableService.treeListCollection();
    }
    @PutMapping("/putGroup")
    private void putGroup(@RequestParam("id") Long id,@RequestParam("groupId") Long groupId){
         collectionTableService.putGroup(id,groupId);
    }

    /**------------------------------------------------------------------------------------------**/
    /**
     * 获取已发布的收集表括弧根据登陆人 管理员看全部
     */
     @GetMapping("/userCollectionTable")
     private IPage<CollectionTableDetailed> userCollectionTable(int pageNum,int pageSize,Long collectionTableParentId,String staticZ,Long numberYearId){
      return   collectionTableService.userCollectionTable(pageNum,pageSize,collectionTableParentId,staticZ,numberYearId);
     }
     @GetMapping("/applyForUpdateCollectionTable")
     private IPage<CollectionTableDetailedTextVo>  applyForUpdateCollectionTable(int pageNum,int pageSize,Long numberYearId,Long departmentId,Long userId,Long collectionTableParentId,Long collectionTableId){
            return collectionTableService.applyForUpdateCollectionTable(pageNum,pageSize,numberYearId,departmentId,userId,collectionTableParentId,collectionTableId);
     }

     @PostMapping("/saveUserCollectionTable")
     private void saveUserCollectionTable(@RequestBody CollectionTableDetailedText collectionTableDetailedText){
         collectionTableService.saveUserCollectionTable(collectionTableDetailedText);
     }
     @GetMapping("/getUserByCollectionTableDetailedTextList")
     private List<CollectionTableDetailedTextVo> getUserByCollectionTableDetailedTextList( Long collectionTableDetailedId){
        return collectionTableService.getUserByCollectionTableDetailedTextList(collectionTableDetailedId);
     }
     @PostMapping("/updateCollectionTableDetailedTextList")
     private void updateCollectionTableDetailedTextList(@RequestBody CollectionTableDetailedText collectionTableDetailedText){
         collectionTableService.updateCollectionTableDetailedTextList(collectionTableDetailedText);
     }
     @GetMapping("/userApplyForUpdateCollectionTable")
     private String userApplyForUpdateCollectionTable(@RequestParam("id") Long id){
         collectionTableService.userApplyForUpdateCollectionTable(id);
         return "已通知高级管理员 ！！！！等待邮箱回复";
     }
     @GetMapping("/userApplyForUpdateCollectionTableAdmin")
     private void userApplyForUpdateCollectionTableAdmin( Long userId, Long id, boolean flag){
        collectionTableService.userApplyForUpdateCollectionTableAdmin(userId,id,flag);
     }
    @DeleteMapping("/deleteCollectionTableDetailedText")
    private void deleteCollectionTableDetailedText(Long id){
        collectionTableService.deleteCollectionTableDetailedText(id);
    }
}
