package com.ctts.service.impl;

import com.ctts.entity.Project;
import com.ctts.mapper.ProjectMapper;
import com.ctts.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 实训项目业务实现类
 */
@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectMapper projectMapper;

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
}
