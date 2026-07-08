package com.platform.dto;

import lombok.Data;

@Data
public class RegisterDTO {
    private String studentNo;
    private String name;
    private String idCardSuffix;
    private String college;
}

@Data
class LoginDTO {
    private String studentNo;
}

@Data
class TaskPublishDTO {
    private String title;
    private String description;
    private Integer reward;
    private String category;
    private Integer minCreditScore;
}
