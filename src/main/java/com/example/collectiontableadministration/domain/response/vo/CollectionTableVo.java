package com.example.collectiontableadministration.domain.response.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.example.collectiontableadministration.domain.pojo.CollectionTableDetailed;
import lombok.Data;

import java.util.List;

/**
 * @author 杨子涵
 */
@Data
public class CollectionTableVo {
    @TableId(type= IdType.ASSIGN_ID)
    private  Long id;
    private  String collectionTableName;
    private  Long parentId;
    private List<CollectionTableDetailed> collectionTableDetailedList;
     private List<CollectionTableVo> children;
}
