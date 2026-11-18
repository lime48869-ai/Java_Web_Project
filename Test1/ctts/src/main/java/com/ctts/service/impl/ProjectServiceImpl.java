package com.ctts.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ctts.entity.Project;
import com.ctts.entity.Task;
import com.ctts.exception.BusinessException;
import com.ctts.mapper.ProjectMapper;
import com.ctts.mapper.TaskMapper;
import com.ctts.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 实训项目业务实现类
 */
@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectMapper projectMapper;

    @Autowired
    private TaskMapper taskMapper;

    @Override
    public List<Project> getAllProjects() {
        return projectMapper.selectList(null);
    }

    @Override
    public Project getById(Long id) {
        return projectMapper.selectById(id);
    }

    @Override
    public void save(Project project) {
        projectMapper.insert(project);
    }

    @Override
    public List<Project> getByTitle(String title) {
        return projectMapper.getByTitle(title);
    }

    /**
     * 删除项目，并级联删除该项目下的所有任务
     * 两次写操作必须在同一个事务里：任务删掉了、项目没删掉，数据就不一致了
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteCascade(Long projectId) {
        Project project = projectMapper.selectById(projectId);
        if (project == null) {
            throw new BusinessException(404, "项目不存在，无法删除");
        }

        // 先删子表：该项目下的所有任务
        taskMapper.delete(new LambdaQueryWrapper<Task>()
                .eq(Task::getProjectId, projectId));

        // 再删主表：项目本身
        projectMapper.deleteById(projectId);
    }
}
