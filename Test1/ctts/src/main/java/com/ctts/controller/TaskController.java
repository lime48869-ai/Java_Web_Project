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

    /**
     * 查询任务列表
     * GET /tasks
     */
    @GetMapping
    public Result<List<Task>> getTasks() {
        return Result.success(taskService.getTasks());
    }

    /**
     * 新增任务
     * POST /tasks
     */
    @PostMapping
    public Result<String> addTask(@RequestBody TaskDTO taskDTO) {
        Task task = new Task();
        BeanUtils.copyProperties(taskDTO, task);
        taskService.addTask(task);
        return Result.success("添加成功");
    }

    /**
     * 根据ID查询任务
     * GET /tasks/{id}
     */
    @GetMapping("/{id}")
    public Result<Task> getTask(@PathVariable Long id) {
        return Result.success(taskService.getById(id));
    }

    /**
     * 修改任务
     * PUT /tasks/{id}
     */
    @PutMapping("/{id}")
    public Result<String> updateTask(@PathVariable Long id, @RequestBody TaskDTO taskDTO) {
        Task task = new Task();
        BeanUtils.copyProperties(taskDTO, task);
        task.setId(id);
        taskService.updateTask(task);
        return Result.success("修改成功");
    }

    /**
     * 删除任务
     * DELETE /tasks/{id}
     */
    @DeleteMapping("/{id}")
    public Result<String> deleteTask(@PathVariable Long id) {
        taskService.deleteById(id);
        return Result.success("删除成功");
    }
}
