package com.platform.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("credit_log")
public class CreditLog {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long userId;
    private Integer changeScore;
    private Integer balanceBefore;
    private Integer balanceAfter;
    private String reason;
    private Long relatedOrderId;
    private LocalDateTime createTime;
}
