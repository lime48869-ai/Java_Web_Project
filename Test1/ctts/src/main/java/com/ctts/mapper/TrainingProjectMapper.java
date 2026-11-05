package com.ctts.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ctts.entity.TrainingProject;

import java.util.List;

/**
 * 【超前内容】本文件为早期实训项目 Mapper 练习，当前主线已统一使用
 * mapper.ProjectMapper，本文件保留备查。
 *
 * 实训项目 Mapper 接口
 * - 继承 BaseMapper<TrainingProject> 获得内置 CRUD
 * - 自定义方法用 XML SQL 实现（见 resources/mapper/TrainingProjectMapper.xml）
 */
public interface TrainingProjectMapper extends BaseMapper<TrainingProject> {

    /** 按状态查询项目列表 */
    List<TrainingProject> selectByStatus(Integer status);

    /** 按教师ID查询其负责的项目 */
    List<TrainingProject> selectByTeacherId(Long teacherId);

    /** 按主键查询（自定义XML实现） */
    TrainingProject selectByIdCustom(Long id);

    /** 新增项目（XML实现） */
    int insertByXml(TrainingProject project);

    /** 按状态统计项目数量 */
    long countByStatus(Integer status);

}
