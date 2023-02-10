package com.example.collectiontableadministration.domain.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author 杨子涵
 */
@Data
@TableName("collection_table")
public class CollectionTable {
    @TableId(type= IdType.ASSIGN_ID)
    private  Long id;
    private  String collectionTableName;
    private  Long parentId;
}
