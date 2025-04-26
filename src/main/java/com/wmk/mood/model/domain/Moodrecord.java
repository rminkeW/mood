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
 * @TableName moodrecord
 */
@TableName(value ="moodrecord")
@Data
public class Moodrecord implements Serializable {
    /**
     * 记录id
     */
    @TableId(type = IdType.AUTO)
    private Long recordId;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 用户记录情绪
     */
    private String emotion;

    /**
     * 情绪记录时间
     */
    private String recordTime;

    /**
     * 创建时间（数据插入时间）
     */
    private LocalDateTime createTime;

    /**
     * 更新时间（数据更新时间）
     */
    private LocalDateTime updateTime;

    /**
     * 是否删除0-1（逻辑删除）
     */
    @TableLogic
    private Integer idDelete;
    private static final long serialVersionUID = 1L;
}