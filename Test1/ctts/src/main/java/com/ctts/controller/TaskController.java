package com.ctts.controller;

import com.ctts.common.Result;
import com.ctts.dto.TaskDTO;
import com.ctts.entity.Task;
import com.ctts.service.TaskService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @GetMapping
    public Result<List<Task>> getTasks() {
        return Result.success(taskService.getTasks());
    }

    @PostMapping
    public Result<String> addTask(@RequestBody TaskDTO taskDTO) {
        Task task = new Task();
        BeanUtils.copyProperties(taskDTO, task);
        taskService.addTask(task);
        return Result.success("添加成功");
    }
}
