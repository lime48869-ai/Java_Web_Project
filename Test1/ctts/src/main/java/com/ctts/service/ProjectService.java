package com.ctts.service;

import com.ctts.entity.Project;
import java.util.List;

public interface ProjectService {
    List<Project> list(String keyword, int page, int size);
    Project getById(Long id);
    Project add(Project project);
}
