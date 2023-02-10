package com.example.collectiontableadministration.domain.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("collection_table_detailed")
public class CollectionTableDetailed {
    @TableId(type= IdType.ASSIGN_ID)
    private Long  id;
    private Long departmentId;
    @TableField(exist = false)
    private String departmentName;
    private Long numberYearId;
    @TableField(exist = false)
    private String numberYearName;
    @JsonFormat(shape =JsonFormat.Shape.STRING,pattern ="yyyy-MM-dd HH:mm:ss",timezone ="GMT+8")
    private LocalDateTime expirationTime;
    @TableField("static")
    private String staticZ;
    private Long collectionTableId;
    private Long collectionTableParentId;
    @TableField(exist = false)
    private String collectionTableName;
    private Long groupId;
    @TableField(exist = false)
    private String groupName;
}
