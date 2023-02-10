package com.example.collectiontableadministration.domain.comm;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author 杨子涵 实体返回基础类
 */
@Data
public class Basics {
    /**
     * 删除状态
     */
    private boolean state;
    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    /**
     * 创建用户
     */
    @TableField(fill = FieldFill.INSERT)
    private long createUser;
    /**
     * 修改时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
    /**
     * 修改用户
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private long updateUser;
}
