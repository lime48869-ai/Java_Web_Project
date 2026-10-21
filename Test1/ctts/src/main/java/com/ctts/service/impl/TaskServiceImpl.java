package com.ctts.service.impl;

import com.ctts.dao.TaskDAO;
import com.ctts.entity.Task;
import com.ctts.service.TaskService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {

    @Override
    public List<Task> getTasks() {
        TaskDAO taskDAO = new TaskDAO();
        return taskDAO.getTasks();
    }

    @Override
    public void addTask(Task task) {
        TaskDAO taskDAO = new TaskDAO();
        taskDAO.addTask(task);
    }
}
