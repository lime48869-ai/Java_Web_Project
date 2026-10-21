package com.ctts.controller;

import com.ctts.entity.Project;
import com.ctts.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 实训项目控制器
 * 提供：列表查询、根据ID查询、新增项目 三个接口
 */
@RestController
@RequestMapping("/projects")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    /**
     * 接口1：查询实训项目列表
     * GET /projects?keyword=电商&page=1&size=10
     */
    @GetMapping
    public List<Project> list(
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {
        return projectService.list(keyword, page, size);
    }

    /**
     * 接口2：根据 ID 查询
     * GET /projects/1
     */
    @GetMapping("/{id}")
    public Project getById(@PathVariable Long id) {
        return projectService.getById(id);
    }

    /**
     * 接口3：新增项目
     * POST /projects
     * Body: {"name":"AI 训练实训","semester":"2025 春季","description":"..."}
     */
    @PostMapping
    public Project add(@RequestBody Project project) {
        return projectService.add(project);
    }
}
