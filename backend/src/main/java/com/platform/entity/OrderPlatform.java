package com.platform.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("order_platform")
public class OrderPlatform {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long taskId;
    private Long payerId;
    private Long payeeId;
    private Integer amount;
    private String status;
    private Boolean disputeFlag;
    private LocalDateTime completeTime;
    @TableLogic
    private Integer deleted;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
