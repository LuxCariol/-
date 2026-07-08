package com.platform.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CreditChangeReason {
    ORDER_COMPLETE("订单完成加分"),
    ORDER_ABANDON("接单未完成扣分"),
    DISPUTE_LOSE("纠纷判负扣分"),
    DISPUTE_MINOR("轻微违约扣分"),
    DISPUTE_NORMAL("一般违约扣分"),
    DISPUTE_MALICIOUS("恶意违约扣分"),
    INVITE("邀请新用户加分"),
    GUARANTOR_PENALTY("担保人连带扣分"),
    GUARANTOR_REWARD("担保人奖励加分"),
    NEW_USER_GUIDE("新手任务加分"),
    TASK_BOUNTY("任务悬赏加分");

    private final String description;
}
