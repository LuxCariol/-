package com.platform.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.platform.entity.CreditLog;
import com.platform.entity.Task;
import com.platform.entity.User;
import com.platform.mapper.CreditLogMapper;
import com.platform.mapper.OrderMapper;
import com.platform.mapper.UserMapper;
import com.platform.service.CreditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CreditServiceImpl implements CreditService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private CreditLogMapper creditLogMapper;

    @Autowired
    private OrderMapper orderMapper;

    /**
     * 信用分计算公式：
     * 总分 = 基础分(600) + 履约质量分(权重55%) + 担保人质量分(权重20%) + 近期活跃度分(权重15%) + 社交贡献分(权重10%)
     */
    @Override
    public int calculateCreditScore(User user) {
        if (user == null) return 600;

        int baseScore = 600;

        // 1. 履约质量（权重55%）- 采用贝叶斯平均修正
        int completedOrders = orderMapper.countCompletedOrders(user.getId());
        int totalOrders = completedOrders + 0; // 简化：实际需统计总接单数

        double qualityScore;
        if (totalOrders == 0) {
            qualityScore = 0;
        } else {
            // 贝叶斯平均： (已完成订单 + 10) / (总接单 + 12) × 200
            qualityScore = (completedOrders + 10.0) / (totalOrders + 12.0) * 200;
        }
        // 履约质量分超不过200
        qualityScore = Math.min(qualityScore, 200);

        // 2. 担保人质量（权重20%）- 取中位数
        double guarantorScore = 0;
        List<User> guaranteedUsers = userMapper.findGuaranteedUsers(user.getId());
        if (guaranteedUsers != null && !guaranteedUsers.isEmpty()) {
            int[] scores = guaranteedUsers.stream()
                    .filter(u -> u.getCreditScore() != null)
                    .mapToInt(User::getCreditScore)
                    .sorted()
                    .toArray();
            int mid = scores.length / 2;
            guarantorScore = scores.length % 2 == 0 ?
                    (scores[mid - 1] + scores[mid]) / 2.0 - 600:
                    scores[mid] - 600;
            guarantorScore = Math.max(guarantorScore, 0);
        }

        // 3. 近期活跃度（权重15%）- 过去30天完成的订单数，每单+1分，上限30分
        double activityScore = 0;
        // 简化：统计所有已完成订单
        if (completedOrders > 0) {
            activityScore = Math.min(completedOrders, 30);
        }

        // 4. 社交贡献（权重10%）
        int inviteCount = 0; // 可查询invite表获取
        double socialScore = inviteCount * 3;

        // 总分 = 基础分 + 加权各因子得分
        double factorSum = qualityScore * 0.55 + guarantorScore * 0.20 + activityScore * 0.15 + socialScore * 0.10;

        return baseScore + (int) Math.round(factorSum);
    }

    @Override
    public String getCreditLevel(int score) {
        if (score >= 750) return "钻石担保人";
        if (score >= 700) return "黄金学长";
        if (score >= 650) return "白银跑腿";
        return "青铜小白";
    }

    @Override
    @Transactional
    public void recordCreditChange(Long userId, int change, String reason, Long relatedOrderId) {
        User user = userMapper.selectById(userId);
        if (user == null) return;

        int before = user.getCreditScore() != null ? user.getCreditScore() : 600;
        int after = Math.max(0, before + change);

        CreditLog log = new CreditLog();
        log.setUserId(userId);
        log.setChangeScore(change);
        log.setBalanceBefore(before);
        log.setBalanceAfter(after);
        log.setReason(reason);
        log.setRelatedOrderId(relatedOrderId);
        creditLogMapper.insert(log);

        user.setCreditScore(after);
        user.setLevel(getCreditLevel(after));
        userMapper.updateById(user);
    }

    @Override
    @Transactional
    public int deductCredit(Long userId, int amount, String reason, Long relatedOrderId) {
        recordCreditChange(userId, -amount, reason, relatedOrderId);
        User user = userMapper.selectById(userId);
        return user != null ? user.getCreditScore() : 0;
    }

    /**
     * 每日凌晨2:00 增量更新信用分（只处理近24小时有操作的用户）
     */
    @Override
    @Scheduled(cron = "0 0 2 * * ?")
    @Transactional
    public void batchRecalculate() {
        // 简化：实际应查近24小时有订单的用户
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.select("id").lambda().eq(User::getDeleted, 0);
        List<User> allUsers = userMapper.selectList(wrapper);

        // 每两周 改为全量（此处模拟） 简化只更新有credit_score的用户
        LocalDateTime since = LocalDateTime.now().minusHours(24);
        List<CreditLog> recentLogs = creditLogMapper.findRecentByUserId(0L, since);

        // 实际业务中应扫描order_platform表找到近24小时有操作的用户
        // 此处只做增量更新思路示意
    }
}
