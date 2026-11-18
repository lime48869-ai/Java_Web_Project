package com.ctts.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TaskDTO {
    private Long id;

    @NotNull(message = "所属项目不能为空")
    private Long projectId;

    @NotBlank(message = "任务标题不能为空")
    @Size(max = 100, message = "任务标题不能超过 100 个字符")
    private String title;

    @Size(max = 1000, message = "任务描述不能超过 1000 个字符")
    private String description;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @NotNull(message = "截止时间不能为空")
    private LocalDateTime deadline;

    @Min(value = 0, message = "任务满分不能小于 0")
    @Max(value = 100, message = "任务满分不能大于 100")
    private Integer totalScore;

    private Integer status;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime updateTime;
}
