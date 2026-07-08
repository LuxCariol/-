package com.platform.service;

import com.platform.entity.Task;
import com.platform.entity.User;

import java.util.List;

public interface TaskService {
    Task publishTask(Task task);
    String acceptTask(Long taskId, User user);
    String completeTask(Long taskId, User user);
    List<Task> listPendingTasks();
    List<Task> listUserTasks(Long userId);
    Task findById(Long taskId);
}
