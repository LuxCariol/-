package com.platform.service.impl;

import com.platform.common.DisputeLevel;
import com.platform.entity.Guarantor;
import com.platform.entity.User;
import com.platform.mapper.GuarantorMapper;
import com.platform.mapper.UserMapper;
import com.platform.service.CreditService;
import com.platform.service.GuarantorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class GuarantorServiceImpl implements GuarantorService {

    @Autowired
    private GuarantorMapper guarantorMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private CreditService creditService;

    @Override
    @Transactional
    public String inviteGuarantor(Long userId, Long guarantorUserId) {
        // 校验：一个人最多有3个担保人
        Integer count = guarantorMapper.countActiveGuarantors(userId);
        if (count != null && count >= 3) return "已达到担保人上限（3人）";

        // 校验：一个人最多给5个人做担保
        Integer guaranteeing = guarantorMapper.countGuaranteeing(guarantorUserId);
        if (guaranteeing != null && guaranteeing >= 5) return "该同学已达到担保上限（5人）";

        Guarantor guarantor = new Guarantor();
        guarantor.setUserId(userId);
        guarantor.setGuarantorId(guarantorUserId);
        guarantor.setStatus("ACTIVE");
        guarantorMapper.insert(guarantor);

        // 更新用户的担保人计数
        User user = userMapper.selectById(userId);
        if (user != null) {
            user.setGuarantorCount(user.getGuarantorCount() == null ? 1 : user.getGuarantorCount() + 1);
            userMapper.updateById(user);
        }
        return "担保关系建立成功";
    }

    @Override
    @Transactional
    public String removeGuarantor(Long userId, Long guarantorUserId) {
        guarantorMapper.delete(null); // 简化：实际需按条件删除
        User user = userMapper.selectById(userId);
        if (user != null && user.getGuarantorCount() != null && user.getGuarantorCount() > 0) {
            user.setGuarantorCount(user.getGuarantorCount() - 1);
            userMapper.updateById(user);
        }
        return "担保关系已解除";
    }

    @Override
    public List<Guarantor> listGuarantors(Long userId) {
        return guarantorMapper.findActiveGuarantorsByUserId(userId);
    }

    @Override
    public boolean hasValidGuarantor(Long userId) {
        Integer count = guarantorMapper.countActiveGuarantors(userId);
        return count != null && count > 0;
    }

    @Override
    public int countActiveGuarantors(Long userId) {
        Integer count = guarantorMapper.countActiveGuarantors(userId);
        return count != null ? count : 0;
    }

    @Override
    public int countGuaranteeing(Long userId) {
        Integer count = guarantorMapper.countGuaranteeing(userId);
        return count != null ? count : 0;
    }

    @Override
    @Transactional
    public void processGuarantorPenalty(Long userId, DisputeLevel level) {
        // 找到所有为该用户担保的人，连带扣分
        List<Guarantor> guarantors = guarantorMapper.findActiveGuarantorsByUserId(userId);
        for (Guarantor g : guarantors) {
            creditService.deductCredit(g.getGuarantorId(), level.getGuarantorDeduction(),
                    "GUARANTOR_PENALTY", null);
        }

        // 检查担保人是否连续为2名以上恶意违约用户担保
        if (level == DisputeLevel.MALICIOUS) {
            for (Guarantor g : guarantors) {
                List<User> guaranteedUsers = userMapper.findGuaranteedUsers(g.getGuarantorId());
                long maliciousCount = 0;
                for (User u : guaranteedUsers) {
                    if (u.getCreditScore() != null && u.getCreditScore() < 600) {
                        maliciousCount++;
                    }
                }
                if (maliciousCount >= 2) {
                    // 取消该担保人的担保资格
                    guarantorMapper.findActiveByGuarantorId(g.getGuarantorId())
                            .forEach(gr -> {
                                gr.setStatus("DISABLED");
                                guarantorMapper.updateById(gr);
                            });
                }
            }
        }
    }

    @Override
    public boolean isGuarantorQualified(User user) {
        // 低分用户（<600）需要2名750分以上用户担保
        List<Guarantor> guarantors = guarantorMapper.findActiveGuarantorsByUserId(user.getId());
        if (guarantors == null || guarantors.size() < 2) return false;
        int qualifiedCount = 0;
        for (Guarantor g : guarantors) {
            User guarantorUser = userMapper.selectById(g.getGuarantorId());
            if (guarantorUser != null && guarantorUser.getCreditScore() >= 750) {
                qualifiedCount++;
            }
        }
        return qualifiedCount >= 2;
    }
}
