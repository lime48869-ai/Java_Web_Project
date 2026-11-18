package com.ctts.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ctts.common.Result;
import com.ctts.dto.TaskDTO;
import com.ctts.entity.Task;
import com.ctts.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
@Tag(name = "任务管理", description = "任务的增删改查、多条件查询与分页")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @GetMapping
    @Operation(summary = "查询任务列表")
    public Result<List<Task>> getTasks() {
        return Result.success(taskService.getTasks());
    }

    @PostMapping
    @Operation(summary = "新增任务")
    public Result<String> addTask(@Valid @RequestBody TaskDTO taskDTO) {
        Task task = new Task();
        BeanUtils.copyProperties(taskDTO, task);
        taskService.addTask(task);
        return Result.success("添加成功");
    }

    @GetMapping("/{id}")
    @Operation(summary = "按ID查询任务")
    public Result<Task> getTask(@Parameter(description = "任务ID") @PathVariable Long id) {
        return Result.success(taskService.getById(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "修改任务")
    public Result<String> updateTask(@Parameter(description = "任务ID") @PathVariable Long id,
                                     @Valid @RequestBody TaskDTO taskDTO) {
        Task task = new Task();
        BeanUtils.copyProperties(taskDTO, task);
        task.setId(id);
        taskService.updateTask(task);
        return Result.success("修改成功");
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除任务")
    public Result<String> deleteTask(@Parameter(description = "任务ID") @PathVariable Long id) {
        taskService.deleteById(id);
        return Result.success("删除成功");
    }

    @GetMapping("/options")
    @Operation(summary = "多条件查询任务（项目ID、状态精确匹配，标题模糊匹配）")
    public Result<List<Task>> getTasksByOptions(@Parameter(description = "项目ID，可不传") @RequestParam(required = false) Long projectId,
                                                @Parameter(description = "状态：1未开始 2进行中 3已截止") @RequestParam(required = false) Integer status,
                                                @Parameter(description = "标题关键字，模糊匹配") @RequestParam(required = false) String title) {
        return Result.success(taskService.getTasksByOptions(projectId, status, title));
    }

    @GetMapping("/status")
    @Operation(summary = "按状态集合查询任务")
    public Result<List<Task>> getTasksByStatusList(@Parameter(description = "状态集合，逗号分隔") @RequestParam List<Integer> statusList) {
        return Result.success(taskService.getTasksByStatusList(statusList));
    }

    @PostMapping("/getByOptions")
    @Operation(summary = "条件构造器查询任务（LambdaQueryWrapper）")
    public Result<List<Task>> getTasksByOptions(@RequestBody TaskDTO taskDTO) {
        return Result.success(taskService.getByOptions(taskDTO));
    }

    @GetMapping("/page")
    @Operation(summary = "分页查询任务")
    public Result<IPage<Task>> getTasksByPage(@Parameter(description = "页码，从1开始") @RequestParam int pageNum,
                                              @Parameter(description = "每页条数") @RequestParam int pageSize) {
        IPage<Task> page = new Page<>(pageNum, pageSize);
        return Result.success(taskService.getTasksByPage(page));
    }

}
