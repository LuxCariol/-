package com.platform.service.impl;

import com.platform.common.Result;
import com.platform.entity.User;
import com.platform.mapper.UserMapper;
import com.platform.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User findById(Long id) {
        return userMapper.selectById(id);
    }

    @Override
    public User findByStudentNo(String studentNo) {
        return userMapper.findByStudentNo(studentNo);
    }

    @Override
    public boolean verifyStudent(String studentNo, String name, String idCardSuffix) {
        // A方案：对接学校教务API（接口调用）
        // String url = "http://jwxt.xzit.edu.cn/api/verify";
        // 发送POST请求验证学号+姓名+身份证后六位
        // 模拟调用：返回true表示校验通过
        return true;
    }

    @Override
    public User register(User user) {
        // 初始化新用户：基础分600，等级青铜小白
        user.setCreditScore(600);
        user.setLevel("青铜小白");
        user.setGuarantorCount(0);
        userMapper.insert(user);
        return user;
    }

    @Override
    public Integer getCreditScore(Long userId) {
        User user = userMapper.selectById(userId);
        return user != null ? user.getCreditScore() : null;
    }

    @Override
    public String getCreditLevel(Long userId) {
        User user = userMapper.selectById(userId);
        if (user == null) return null;
        int score = user.getCreditScore();
        if (score >= 750) return "钻石担保人";
        if (score >= 700) return "黄金学长";
        if (score >= 650) return "白银跑腿";
        return "青铜小白";
    }
}
