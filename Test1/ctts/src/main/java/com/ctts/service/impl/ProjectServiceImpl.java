package com.ctts.service.impl;

import com.ctts.entity.Project;
import com.ctts.service.ProjectService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
public class ProjectServiceImpl implements ProjectService {

    // 模拟数据库：线程安全 Map
    private static final ConcurrentHashMap<Long, Project> DB = new ConcurrentHashMap<>();
    private static final AtomicLong ID_GEN = new AtomicLong(0);

    static {
        // 初始化两条模拟数据
        Project p1 = new Project(1L, "电商实训", "2025 春季");
        p1.setDescription("搭建小型电商平台，包含商品、订单、支付");
        p1.setInternalRemark("校长牵头");
        DB.put(1L, p1);

        Project p2 = new Project(2L, "大数据实训", "2025 春季");
        p2.setDescription("基于 Hadoop 生态完成离线数仓");
        p2.setInternalRemark("企业合作");
        DB.put(2L, p2);

        ID_GEN.set(2L);
    }

    @Override
    public List<Project> list(String keyword, int page, int size) {
        List<Project> all = new ArrayList<>(DB.values());
        if (keyword != null && !keyword.isEmpty()) {
            all = all.stream()
                    .filter(p -> p.getName().contains(keyword))
                    .collect(Collectors.toList());
        }
        // 简单分页
        int from = (page - 1) * size;
        int to = Math.min(from + size, all.size());
        if (from >= all.size()) {
            return new ArrayList<>();
        }
        return all.subList(from, to);
    }

    @Override
    public Project getById(Long id) {
        return DB.get(id);
    }

    @Override
    public Project add(Project project) {
        long id = ID_GEN.incrementAndGet();
        project.setId(id);
        project.setCreateTime(java.time.LocalDateTime.now());
        if (project.getStatus() == null) {
            project.setStatus(0);
        }
        DB.put(id, project);
        return project;
    }
}
