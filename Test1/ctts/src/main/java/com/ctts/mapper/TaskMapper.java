package com.ctts.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ctts.entity.Task;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 任务 Mapper 接口
 * 继承 BaseMapper<Task> 获得内置 CRUD
 */
public interface TaskMapper extends BaseMapper<Task> {

    /**
     * 按项目ID查询任务
     */
    @Select("SELECT * FROM task WHERE project_id = #{projectId} ORDER BY deadline ASC")
    List<Task> selectByProjectId(Long projectId);

    /**
     * 按状态查询任务
     */
    @Select("SELECT * FROM task WHERE status = #{status} ORDER BY deadline DESC")
    List<Task> selectByStatus(Integer status);
}
