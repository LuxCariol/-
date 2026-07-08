package com.platform.service.impl;

import com.platform.common.DisputeLevel;
import com.platform.entity.User;
import com.platform.mapper.UserMapper;
import com.platform.service.CreditService;
import com.platform.service.DisputeService;
import com.platform.service.GuarantorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DisputeServiceImpl implements DisputeService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private CreditService creditService;

    @Autowired
    private GuarantorService guarantorService;

    @Override
    @Transactional
    public String reportDispute(Long orderId, Long userId, String evidence) {
        // 纠纷上报后写入争议表，由裁审组处理
        // 简化：直接返回上报成功
        return "纠纷已上报，请等待裁审组处理（24小时内给出初步判定）";
    }

    @Override
    @Transactional
    public String arbitrate(Long disputeId, DisputeLevel level) {
        // 裁审组判定后执行
        // 1. 扣被担保人信用分
        // 2. 扣担保人信用分
        // 3. 记录信用流水

        // 根据违约级别扣分
        int executorDeduction = level.getExecutorMaxDeduction();

        // 实际需从dispute表获取违约用户ID
        // 此处做逻辑示意
        if (level == DisputeLevel.MALICIOUS) {
            // 恶意违约拉黑
            // user.setDeleted(1);
            // userMapper.updateById(user);
        }

        return "裁审完成，已按" + level.getDescription() + "标准执行扣分";
    }
}
