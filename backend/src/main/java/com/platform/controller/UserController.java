package com.platform.controller;

import com.platform.common.Result;
import com.platform.entity.User;
import com.platform.service.UserService;
import com.platform.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/register")
    public Result<Map<String, Object>> register(@RequestBody User user) {
        boolean verified = userService.verifyStudent(user.getStudentNo(), user.getName(), "");
        if (!verified) {
            return Result.error("学籍认证失败，请检查学号与姓名");
        }
        User registered = userService.register(user);
        String token = jwtUtil.generateToken(registered.getId(), registered.getStudentNo());
        Map<String, Object> result = new HashMap<>();
        result.put("user", registered);
        result.put("token", token);
        return Result.success(result);
    }

    @PostMapping("/login")
    public Result<Map<String, Object>> login(@RequestBody Map<String, String> params) {
        String studentNo = params.get("studentNo");
        User user = userService.findByStudentNo(studentNo);
        if (user == null) {
            return Result.error("用户不存在，请先注册");
        }
        String token = jwtUtil.generateToken(user.getId(), user.getStudentNo());
        Map<String, Object> result = new HashMap<>();
        result.put("user", user);
        result.put("token", token);
        return Result.success(result);
    }

    @GetMapping("/{id}")
    public Result<User> getUser(@PathVariable Long id) {
        User user = userService.findById(id);
        if (user == null) {
            return Result.notFound("用户不存在");
        }
        return Result.success(user);
    }

    @GetMapping("/{id}/credit")
    public Result<Map<String, Object>> getCredit(@PathVariable Long id) {
        Integer score = userService.getCreditScore(id);
        String level = userService.getCreditLevel(id);
        Map<String, Object> result = new HashMap<>();
        result.put("score", score);
        result.put("level", level);
        return Result.success(result);
    }
}
