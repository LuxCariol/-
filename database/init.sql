-- ============================================
-- 数据库初始化脚本
-- 信用支撑型高校闲置技能与时间零工服务微校园平台
-- 数据库: campus_zero_work
-- ============================================

CREATE DATABASE IF NOT EXISTS campus_zero_work
    DEFAULT CHARACTER SET utf8mb4
    DEFAULT COLLATE utf8mb4_unicode_ci;

USE campus_zero_work;

-- 用户表
CREATE TABLE IF NOT EXISTS `user` (
    `id`            BIGINT       NOT NULL AUTO_INCREMENT COMMENT '用户ID',
    `student_no`    VARCHAR(20)  NOT NULL COMMENT '学号',
    `name`          VARCHAR(50)  NOT NULL COMMENT '姓名',
    `college`       VARCHAR(100) NOT NULL DEFAULT '' COMMENT '学院',
    `credit_score`  INT          NOT NULL DEFAULT 600 COMMENT '信用分（冗余存储）',
    `level`         VARCHAR(20)  NOT NULL DEFAULT '青铜小白' COMMENT '信用等级',
    `guarantor_count` INT        NOT NULL DEFAULT 0 COMMENT '担保人数量',
    `deleted`       TINYINT      NOT NULL DEFAULT 0 COMMENT '逻辑删除 0-正常 1-删除',
    `create_time`   DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`   DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_student_no` (`student_no`),
    KEY `idx_credit_score` (`credit_score`),
    KEY `idx_college` (`college`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

-- 任务表
CREATE TABLE IF NOT EXISTS `task` (
    `id`               BIGINT       NOT NULL AUTO_INCREMENT COMMENT '任务ID',
    `publisher_id`     BIGINT       NOT NULL COMMENT '发布人ID',
    `executor_id`      BIGINT       DEFAULT NULL COMMENT '接单人ID',
    `title`            VARCHAR(100) NOT NULL COMMENT '标题',
    `description`      TEXT         COMMENT '描述',
    `reward`           INT          NOT NULL DEFAULT 0 COMMENT '报酬（信用分）',
    `category`         VARCHAR(20)  NOT NULL DEFAULT 'running' COMMENT '品类 running-跑腿 skill-技能 art-才艺',
    `min_credit_score` INT          NOT NULL DEFAULT 600 COMMENT '最低信用分门槛',
    `status`           VARCHAR(20)  NOT NULL DEFAULT 'PENDING' COMMENT '状态 PENDING/ACCEPTED/IN_PROGRESS/COMPLETED/DISPUTED',
    `deleted`          TINYINT      NOT NULL DEFAULT 0 COMMENT '逻辑删除',
    `create_time`      DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`      DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    KEY `idx_publisher` (`publisher_id`),
    KEY `idx_executor` (`executor_id`),
    KEY `idx_status` (`status`),
    KEY `idx_category` (`category`),
    KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='任务表';

-- 订单表
CREATE TABLE IF NOT EXISTS `order_platform` (
    `id`            BIGINT       NOT NULL AUTO_INCREMENT COMMENT '订单ID',
    `task_id`       BIGINT       NOT NULL COMMENT '任务ID',
    `payer_id`      BIGINT       NOT NULL COMMENT '付款方用户ID',
    `payee_id`      BIGINT       NOT NULL COMMENT '收款方用户ID',
    `amount`        INT          NOT NULL DEFAULT 0 COMMENT '金额',
    `status`        VARCHAR(20)  NOT NULL DEFAULT 'PENDING' COMMENT '状态',
    `dispute_flag`  TINYINT      NOT NULL DEFAULT 0 COMMENT '是否有争议 0-无 1-有',
    `complete_time` DATETIME     DEFAULT NULL COMMENT '完成时间',
    `deleted`       TINYINT      NOT NULL DEFAULT 0 COMMENT '逻辑删除',
    `create_time`   DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`   DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    KEY `idx_task_id` (`task_id`),
    KEY `idx_payer` (`payer_id`),
    KEY `idx_payee` (`payee_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单表';

-- 担保关系表
CREATE TABLE IF NOT EXISTS `guarantor` (
    `id`            BIGINT       NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `guarantor_id`  BIGINT       NOT NULL COMMENT '担保人用户ID',
    `user_id`       BIGINT       NOT NULL COMMENT '被担保用户ID',
    `status`        VARCHAR(20)  NOT NULL DEFAULT 'ACTIVE' COMMENT '状态 ACTIVE/DISABLED',
    `deleted`       TINYINT      NOT NULL DEFAULT 0 COMMENT '逻辑删除',
    `create_time`   DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`   DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    KEY `idx_guarantor` (`guarantor_id`),
    KEY `idx_user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='担保关系表';

-- 信用分流水表
CREATE TABLE IF NOT EXISTS `credit_log` (
    `id`               BIGINT       NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `user_id`          BIGINT       NOT NULL COMMENT '用户ID',
    `change_score`     INT          NOT NULL COMMENT '变动的分值（正数加分/负数扣分）',
    `balance_before`   INT          NOT NULL COMMENT '变动前分数',
    `balance_after`    INT          NOT NULL COMMENT '变动后分数',
    `reason`           VARCHAR(50)  NOT NULL COMMENT '变动原因枚举值',
    `related_order_id` BIGINT       DEFAULT NULL COMMENT '关联订单ID',
    `create_time`      DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`),
    KEY `idx_user_id` (`user_id`),
    KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='信用分流水表';

-- 消息表（聊天记录）
CREATE TABLE IF NOT EXISTS `message` (
    `id`          BIGINT       NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `from_user`   BIGINT       NOT NULL COMMENT '发送方用户ID',
    `to_user`     BIGINT       NOT NULL COMMENT '接收方用户ID',
    `task_id`     BIGINT       DEFAULT NULL COMMENT '关联任务ID',
    `content`     TEXT         NOT NULL COMMENT '消息内容',
    `create_time` DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '发送时间',
    PRIMARY KEY (`id`),
    KEY `idx_from_user` (`from_user`),
    KEY `idx_to_user` (`to_user`),
    KEY `idx_task_id` (`task_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='消息表';
