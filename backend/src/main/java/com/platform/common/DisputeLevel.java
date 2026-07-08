package com.platform.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DisputeLevel {
    MINOR("轻微违约", 10, 20, 5),
    NORMAL("一般违约", 30, 30, 15),
    MALICIOUS("恶意违约", 50, 50, 30);

    private final String description;
    private final int executorMinDeduction;
    private final int executorMaxDeduction;
    private final int guarantorDeduction;
}
