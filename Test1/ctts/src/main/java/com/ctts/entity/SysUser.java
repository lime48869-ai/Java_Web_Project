package com.ctts.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 【超前内容】本文件为后续"用户/权限"模块的扩展练习，
 * 当前老师主线（Project/Task）暂未涉及，保留备查。
 *
 * 系统用户实体类
 * 对应数据库表 sys_user
 */
@Data
@TableName("sys_user")
public class SysUser {

    @TableId(type = IdType.AUTO)
    private Long id;

    private String username;        // 登录用户名
    private String password;        // 密码
    private String realName;        // 真实姓名（real_name）
    private String phone;            // 手机号
    private String email;            // 邮箱
    private Integer status;          // 0禁用 1正常

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime updateTime;

    @TableField(exist = false)      // 非表字段，仅用于注册时校验
    private String confirmPassword;
}
