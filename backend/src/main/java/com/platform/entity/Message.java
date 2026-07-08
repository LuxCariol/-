package com.platform.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("message")
public class Message {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long fromUser;
    private Long toUser;
    private Long taskId;
    private String content;
    private LocalDateTime createTime;
}
