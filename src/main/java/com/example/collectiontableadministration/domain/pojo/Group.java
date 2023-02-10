package com.example.collectiontableadministration.domain.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author 32298
 */
@Data
@TableName("sys_group")
public class Group {
    @TableId(type= IdType.ASSIGN_ID)
    private Long id;
    private String groupName;
}
