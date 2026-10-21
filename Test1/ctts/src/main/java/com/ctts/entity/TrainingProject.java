package com.ctts.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 实训项目实体类
 * 对应数据库表 training_project
 */
@Data
@TableName("training_project")
public class TrainingProject {

    @TableId(type = IdType.AUTO)
    private Long id;

    private String title;           // 项目标题
    private String description;      // 项目描述
    private Long teacherId;          // 负责教师ID
    private LocalDate startDate;     // 开始日期
    private LocalDate endDate;       // 结束日期
    private Integer status;          // 1待发布 2进行中 3已结束 4已下线

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime updateTime;
}
