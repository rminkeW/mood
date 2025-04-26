package com.wmk.mood.model.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 
 * @TableName avatar
 */
@TableName(value ="avatar")
@Data
public class Avatar implements Serializable {
    /**
     * 分身id
     */
    @TableId(type = IdType.AUTO)
    private Long avatarId;

    /**
     * 创建分身的用户id
     */
    private Long userId;

    /**
     * 分身名称
     */
    private String avatarName;

    /**
     * 分身性格
     */
    private String avatarCharacter;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 逻辑删除
     */
    @TableLogic
    private Integer isDelete;
    private static final long serialVersionUID = 1L;
}