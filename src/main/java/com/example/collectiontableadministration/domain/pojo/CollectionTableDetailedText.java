package com.example.collectiontableadministration.domain.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("collection_table_detailed_text")
public class CollectionTableDetailedText {

    private Long id;

    private Long collectionTableDetailedId;

    @TableField(exist = false)
    private CollectionTableDetailedExcel collectionTableDetailedExcel;

    private String collectionTableDetailedTextJson;

    private Long userId;

    private boolean applyFor;

}
