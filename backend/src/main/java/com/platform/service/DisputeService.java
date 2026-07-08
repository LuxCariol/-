package com.platform.service;

import com.platform.entity.User;
import com.platform.common.DisputeLevel;

public interface DisputeService {
    String reportDispute(Long orderId, Long userId, String evidence);
    String arbitrate(Long disputeId, DisputeLevel level);
}
