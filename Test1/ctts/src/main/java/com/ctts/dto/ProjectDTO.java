package com.ctts.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 实训项目数据传输对象
 */
@Data
public class ProjectDTO {

    private Long id;

    @NotBlank(message = "项目名称不能为空")
    @Size(max = 100, message = "项目名称不能超过 100 个字符")
    private String title;

    @Size(max = 500, message = "项目描述不能超过 500 个字符")
    private String description;

    @NotNull(message = "指导教师不能为空")
    private Long teacherId;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @NotNull(message = "开始日期不能为空")
    private LocalDate startDate;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @NotNull(message = "结束日期不能为空")
    private LocalDate endDate;

    private Integer status;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime updateTime;

}
