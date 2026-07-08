package com.platform.service.impl;

import com.platform.common.Result;
import com.platform.common.TaskStatus;
import com.platform.entity.Guarantor;
import com.platform.entity.Task;
import com.platform.entity.User;
import com.platform.mapper.GuarantorMapper;
import com.platform.mapper.TaskMapper;
import com.platform.mapper.UserMapper;
import com.platform.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskMapper taskMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private GuarantorMapper guarantorMapper;

    @Override
    @Transactional
    public Task publishTask(Task task) {
        task.setStatus(TaskStatus.PENDING.name());
        task.setMinCreditScore(task.getMinCreditScore() != null ? task.getMinCreditScore() : 600);
        taskMapper.insert(task);
        return task;
    }

    @Override
    @Transactional
    public String acceptTask(Long taskId, User user) {
        Task task = taskMapper.selectById(taskId);
        if (task == null) return "任务不存在";
        if (!TaskStatus.PENDING.name().equals(task.getStatus())) return "该任务已被接单";

        int score = user.getCreditScore() != null ? user.getCreditScore() : 600;
        int requiredScore = task.getMinCreditScore() != null ? task.getMinCreditScore() : 600;
        if (score < requiredScore) return "信用分不满足任务要求";

        // 路径A/B/C 校验
        if (score >= 720) {
            // 路径B：高分用户免担保
        } else if (score >= 600) {
            // 路径A：需要至少1名担保人
            Integer count = guarantorMapper.countActiveGuarantors(user.getId());
            if (count == null || count < 1) return "接单需要至少1名有效担保人";
        } else {
            // 路径C：需要2名750分以上用户担保
            List<Guarantor> guarantors = guarantorMapper.findActiveGuarantorsByUserId(user.getId());
            if (guarantors == null || guarantors.size() < 2) return "信用分不足，需要至少2名高信用用户担保";

            int qualifiedCount = 0;
            for (Guarantor g : guarantors) {
                User guarantorUser = userMapper.selectById(g.getGuarantorId());
                if (guarantorUser != null && guarantorUser.getCreditScore() >= 750) {
                    qualifiedCount++;
                }
            }
            if (qualifiedCount < 2) return "担保人信用分不足750，不符合破格接单条件";
        }

        // 检查未完成订单数
        // ORDER BY create_time DESC LIMIT 1 模拟 - 实际需查询order表
        task.setExecutorId(user.getId());
        task.setStatus(TaskStatus.ACCEPTED.name());
        taskMapper.updateById(task);
        return "接单成功";
    }

    @Override
    @Transactional
    public String completeTask(Long taskId, User user) {
        Task task = taskMapper.selectById(taskId);
        if (task == null) return "任务不存在";
        task.setStatus(TaskStatus.COMPLETED.name());
        taskMapper.updateById(task);
        return "任务已完成";
    }

    @Override
    public List<Task> listPendingTasks() {
        return taskMapper.findPendingTasks();
    }

    @Override
    public List<Task> listUserTasks(Long userId) {
        return taskMapper.findUserTasks(userId);
    }

    @Override
    public Task findById(Long taskId) {
        return taskMapper.selectById(taskId);
    }
}
