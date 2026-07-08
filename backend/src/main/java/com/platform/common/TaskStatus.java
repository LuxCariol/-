package com.platform.common;

public enum TaskStatus {
    PENDING("待接单"),
    ACCEPTED("已接单"),
    IN_PROGRESS("进行中"),
    COMPLETED("已完成"),
    DISPUTED("争议中");

    private final String description;

    TaskStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
