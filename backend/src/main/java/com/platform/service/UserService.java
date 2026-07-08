package com.platform.service;

import com.platform.entity.User;

public interface UserService {
    User findById(Long id);
    User findByStudentNo(String studentNo);
    boolean verifyStudent(String studentNo, String name, String idCardSuffix);
    User register(User user);
    Integer getCreditScore(Long userId);
    String getCreditLevel(Long userId);
}
