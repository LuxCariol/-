package com.platform.service;

import com.platform.entity.Guarantor;
import com.platform.entity.User;
import com.platform.common.DisputeLevel;

import java.util.List;

public interface GuarantorService {
    String inviteGuarantor(Long userId, Long guarantorUserId);
    String removeGuarantor(Long userId, Long guarantorUserId);
    List<Guarantor> listGuarantors(Long userId);
    boolean hasValidGuarantor(Long userId);
    int countActiveGuarantors(Long userId);
    int countGuaranteeing(Long userId);
    void processGuarantorPenalty(Long userId, DisputeLevel level);
    boolean isGuarantorQualified(User user);
}
