package com.platform.service;

import com.platform.entity.User;

public interface CreditService {
    int calculateCreditScore(User user);
    String getCreditLevel(int score);
    void recordCreditChange(Long userId, int change, String reason, Long relatedOrderId);
    int deductCredit(Long userId, int amount, String reason, Long relatedOrderId);
    void batchRecalculate();
}
