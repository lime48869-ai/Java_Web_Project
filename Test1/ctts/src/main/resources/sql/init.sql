-- ============================================================
-- CTTS 数据库初始化脚本（实验三：MyBatis-Plus 环境集成）
-- 在 Navicat / MySQL 命令行中依次执行
-- ============================================================

-- 1. 创建数据库
CREATE DATABASE IF NOT EXISTS ctts
    DEFAULT CHARACTER SET utf8mb4
    DEFAULT COLLATE utf8mb4_general_ci;

USE ctts;

-- 2. 创建 sys_user 系统用户表
DROP TABLE IF EXISTS sys_user;
CREATE TABLE sys_user (
    id          BIGINT       NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    username    VARCHAR(50)  NOT NULL COMMENT '登录用户名',
    password    VARCHAR(100) NOT NULL COMMENT '密码',
    name        VARCHAR(50)  NOT NULL COMMENT '真实姓名',
    role        VARCHAR(20)  NOT NULL COMMENT '角色：admin-管理员 teacher-教师 student-学生',
    phone       VARCHAR(20)  DEFAULT NULL COMMENT '联系电话',
    create_time DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (id),
    UNIQUE KEY uk_username (username)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统用户表';

-- 3. 插入测试数据
INSERT INTO sys_user(username, password, name, role, phone) VALUES
('admin',     '123456', '系统管理员', 'admin',   '13800000001'),
('teacher',   '123456', '张老师',     'teacher', '13800000002'),
('student01', '123456', '李学生',     'student', '13800000003'),
('student02', '123456', '王学生',     'student', '13800000004');

-- 4. 验证
SELECT * FROM sys_user;
