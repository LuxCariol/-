package com.platform.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("task")
public class Task {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long publisherId;
    private Long executorId;
    private String title;
    private String description;
    private Integer reward;
    private String category;
    private Integer minCreditScore;
    private String status;
    @TableLogic
    private Integer deleted;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
