package com.example.collectiontableadministration.domain.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author 32298
 */
@Data
@TableName("user_group")
public class UserGroup {
    @TableId(type= IdType.ASSIGN_ID)
    private Long id;
    private Long userId;
    private Long groupId;
}
