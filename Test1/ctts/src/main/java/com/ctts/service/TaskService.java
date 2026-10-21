package com.ctts.service;

import com.ctts.entity.Task;
import java.util.List;

public interface TaskService {
    List<Task> getTasks();
    void addTask(Task task);
}
