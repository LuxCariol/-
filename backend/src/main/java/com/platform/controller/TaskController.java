package com.platform.controller;

import com.platform.common.Result;
import com.platform.entity.Task;
import com.platform.entity.User;
import com.platform.service.TaskService;
import com.platform.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/task")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private UserService userService;

    @PostMapping("/publish")
    public Result<Task> publish(@RequestBody Task task, @RequestHeader("userId") Long userId) {
        task.setPublisherId(userId);
        Task result = taskService.publishTask(task);
        return Result.success(result);
    }

    @PostMapping("/{taskId}/accept")
    public Result<String> accept(@PathVariable Long taskId, @RequestHeader("userId") Long userId) {
        User user = userService.findById(userId);
        if (user == null) {
            return Result.error("用户不存在");
        }
        String msg = taskService.acceptTask(taskId, user);
        if (msg.contains("成功")) {
            return Result.success(msg);
        }
        return Result.error(msg);
    }

    @PostMapping("/{taskId}/complete")
    public Result<String> complete(@PathVariable Long taskId, @RequestHeader("userId") Long userId) {
        User user = userService.findById(userId);
        if (user == null) {
            return Result.error("用户不存在");
        }
        String msg = taskService.completeTask(taskId, user);
        return Result.success(msg);
    }

    @GetMapping("/list")
    public Result<List<Task>> list() {
        return Result.success(taskService.listPendingTasks());
    }

    @GetMapping("/{id}")
    public Result<Task> detail(@PathVariable Long id) {
        Task task = taskService.findById(id);
        if (task == null) {
            return Result.notFound("任务不存在");
        }
        return Result.success(task);
    }

    @GetMapping("/my")
    public Result<List<Task>> myTasks(@RequestHeader("userId") Long userId) {
        return Result.success(taskService.listUserTasks(userId));
    }
}
