package com.ctts.service;

import com.ctts.entity.Project;

import java.util.List;

/**
 * 实训项目业务接口
 */
public interface ProjectService {

    List<Project> getAllProjects();
    Project getById(Long id);
    void save(Project project);

    List<Project> getByTitle(String title);
}
