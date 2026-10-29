package com.ctts.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ctts.entity.Task;

/**
 * 任务 Mapper 接口
 * 继承 BaseMapper<Task> 获得内置 CRUD
 */
public interface TaskMapper extends BaseMapper<Task> {

    /**
     * 按ID查询任务（XML 自定义实现）
     */
    Task getById(Long id);
}
