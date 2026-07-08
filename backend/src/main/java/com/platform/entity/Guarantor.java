package com.platform.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("guarantor")
public class Guarantor {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long guarantorId;
    private Long userId;
    private String status;
    @TableLogic
    private Integer deleted;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
