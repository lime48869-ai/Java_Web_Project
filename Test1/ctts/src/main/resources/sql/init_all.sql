-- ============================================================
-- CTTS 完整数据库建表脚本（10张表）
-- 按数据库设计文档：https://my-xr.github.io/ctts/05-database-design.html
-- 在 Navicat 中执行
-- ============================================================

USE ctts;

-- 外键约束可能导致删表顺序问题，先全部删（倒序）
DROP TABLE IF EXISTS task_review;
DROP TABLE IF EXISTS task_submission;
DROP TABLE IF EXISTS task;
DROP TABLE IF EXISTS training_project_student;
DROP TABLE IF EXISTS training_project;
DROP TABLE IF EXISTS sys_role_permission;
DROP TABLE IF EXISTS sys_user_role;
DROP TABLE IF EXISTS sys_permission;
DROP TABLE IF EXISTS sys_role;
DROP TABLE IF EXISTS sys_user;

-- ========== 1. sys_user 用户表 ==========
CREATE TABLE sys_user (
    id          BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '用户ID',
    username    VARCHAR(50)     NOT NULL COMMENT '登录用户名',
    password    VARCHAR(100)    NOT NULL COMMENT '密码',
    real_name   VARCHAR(50)     NOT NULL COMMENT '真实姓名',
    phone       VARCHAR(20)     DEFAULT NULL COMMENT '手机号',
    email       VARCHAR(100)    DEFAULT NULL COMMENT '邮箱',
    status      TINYINT UNSIGNED NOT NULL DEFAULT 1 COMMENT '0禁用 1正常',
    create_time DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (id),
    UNIQUE KEY uk_username (username)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

-- ========== 2. sys_role 角色表 ==========
CREATE TABLE sys_role (
    id          BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '角色ID',
    role_code   VARCHAR(50)     NOT NULL COMMENT '角色编码',
    role_name   VARCHAR(50)     NOT NULL COMMENT '角色名称',
    description VARCHAR(255)    DEFAULT NULL COMMENT '角色说明',
    status      TINYINT UNSIGNED NOT NULL DEFAULT 1 COMMENT '0禁用 1正常',
    create_time DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (id),
    UNIQUE KEY uk_role_code (role_code)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色表';

-- ========== 3. sys_permission 权限表 ==========
CREATE TABLE sys_permission (
    id              BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '权限ID',
    permission_code VARCHAR(100)    NOT NULL COMMENT '权限编码',
    permission_name VARCHAR(100)    NOT NULL COMMENT '权限名称',
    resource        VARCHAR(100)    NOT NULL COMMENT '资源名称',
    action          VARCHAR(50)     NOT NULL COMMENT '动作名称',
    description     VARCHAR(255)    DEFAULT NULL COMMENT '权限说明',
    status          TINYINT UNSIGNED NOT NULL DEFAULT 1 COMMENT '0禁用 1正常',
    create_time     DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time     DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (id),
    UNIQUE KEY uk_permission_code (permission_code)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='权限表';

-- ========== 4. sys_user_role 用户角色关联表 ==========
CREATE TABLE sys_user_role (
    id            BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '关联ID',
    user_id       BIGINT UNSIGNED NOT NULL COMMENT '用户ID',
    role_id       BIGINT UNSIGNED NOT NULL COMMENT '角色ID',
    assigned_time DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '分配时间',
    PRIMARY KEY (id),
    UNIQUE KEY uk_user_role (user_id, role_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户角色关联表';

-- ========== 5. sys_role_permission 角色权限关联表 ==========
CREATE TABLE sys_role_permission (
    id            BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '关联ID',
    role_id       BIGINT UNSIGNED NOT NULL COMMENT '角色ID',
    permission_id BIGINT UNSIGNED NOT NULL COMMENT '权限ID',
    assigned_time DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '分配时间',
    PRIMARY KEY (id),
    UNIQUE KEY uk_role_permission (role_id, permission_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色权限关联表';

-- ========== 6. training_project 实训项目表 ==========
CREATE TABLE training_project (
    id          BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '项目ID',
    title       VARCHAR(100)    NOT NULL COMMENT '项目标题',
    description VARCHAR(500)    DEFAULT NULL COMMENT '项目描述',
    teacher_id  BIGINT UNSIGNED NOT NULL COMMENT '负责教师ID',
    start_date  DATE            NOT NULL COMMENT '开始日期',
    end_date    DATE            NOT NULL COMMENT '结束日期',
    status      TINYINT UNSIGNED NOT NULL DEFAULT 1 COMMENT '1待发布 2进行中 3已结束 4已下线',
    create_time DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (id),
    KEY idx_teacher_id (teacher_id),
    KEY idx_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='实训项目表';

-- ========== 7. training_project_student 项目成员关联表 ==========
CREATE TABLE training_project_student (
    id         BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '关联ID',
    project_id BIGINT UNSIGNED NOT NULL COMMENT '项目ID',
    student_id BIGINT UNSIGNED NOT NULL COMMENT '学生ID',
    join_time  DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '加入时间',
    status     TINYINT UNSIGNED NOT NULL DEFAULT 1 COMMENT '0无效 1有效',
    PRIMARY KEY (id),
    UNIQUE KEY uk_project_student (project_id, student_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='项目成员关联表';

-- ========== 8. task 任务表 ==========
CREATE TABLE task (
    id          BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '任务ID',
    project_id  BIGINT UNSIGNED NOT NULL COMMENT '所属项目ID',
    title       VARCHAR(100)    NOT NULL COMMENT '任务标题',
    description VARCHAR(1000)   DEFAULT NULL COMMENT '任务描述',
    deadline    DATETIME        NOT NULL COMMENT '截止时间',
    total_score INT UNSIGNED    NOT NULL DEFAULT 100 COMMENT '任务满分',
    status      TINYINT UNSIGNED NOT NULL DEFAULT 1 COMMENT '1未开始 2进行中 3已截止',
    create_time DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (id),
    KEY idx_project_id (project_id),
    KEY idx_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='任务表';

-- ========== 9. task_submission 任务提交表 ==========
CREATE TABLE task_submission (
    id          BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '提交ID',
    task_id     BIGINT UNSIGNED NOT NULL COMMENT '任务ID',
    user_id     BIGINT UNSIGNED NOT NULL COMMENT '提交学生ID',
    content     TEXT            COMMENT '提交说明',
    file_url    VARCHAR(500)    DEFAULT NULL COMMENT '附件地址',
    submit_time DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '提交时间',
    status      TINYINT UNSIGNED NOT NULL DEFAULT 1 COMMENT '1待评审 2已评审',
    PRIMARY KEY (id),
    KEY idx_task_id (task_id),
    KEY idx_user_id (user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='任务提交表';

-- ========== 10. task_review 任务评审表 ==========
CREATE TABLE task_review (
    id            BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '评审ID',
    submission_id BIGINT UNSIGNED NOT NULL COMMENT '提交ID',
    reviewer_id   BIGINT UNSIGNED NOT NULL COMMENT '评审教师ID',
    score         INT UNSIGNED    NOT NULL COMMENT '评分',
    comment       VARCHAR(500)    DEFAULT NULL COMMENT '评审评语',
    review_time   DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '评审时间',
    PRIMARY KEY (id),
    KEY idx_submission_id (submission_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='任务评审表';

-- ============================================================
-- 初始化测试数据
-- ============================================================

-- 用户
INSERT INTO sys_user(username, password, real_name, phone, email, status) VALUES
('admin',     '123456', '系统管理员', '13800000001', 'admin@ctts.com',  1),
('zhangsan',  '123456', '张老师',     '13800000002', 'zhang@ctts.com',  1),
('lisi',      '123456', '李老师',     '13800000003', 'li@ctts.com',     1),
('student01', '123456', '王学生',     '13800000004', 'wang@ctts.com',   1),
('student02', '123456', '赵学生',     '13800000005', 'zhao@ctts.com',   1);

-- 角色
INSERT INTO sys_role(role_code, role_name, description) VALUES
('ADMIN',   '管理员', '系统管理员'),
('TEACHER', '教师',   '指导教师'),
('STUDENT', '学生',   '学生');

-- 用户角色关联
INSERT INTO sys_user_role(user_id, role_id) VALUES
(1, 1),  -- admin → ADMIN
(2, 2),  -- zhangsan → TEACHER
(3, 2),  -- lisi → TEACHER
(4, 3),  -- student01 → STUDENT
(5, 3);  -- student02 → STUDENT

-- 项目
INSERT INTO training_project(title, description, teacher_id, start_date, end_date, status) VALUES
('2024春季Java实训',   'Java后端开发综合实训',   2, '2024-03-01', '2024-06-30', 2),
('2024春季前端实训',   'Vue3前端开发实训',       3, '2024-03-01', '2024-06-30', 2),
('2023秋季数据库实训', 'MySQL数据库设计与优化', 2, '2023-09-01', '2023-12-31', 3),
('2024暑期项目实训',   '全栈项目实战',           3, '2024-07-01', '2024-08-31', 1);

-- 任务（task表）
INSERT INTO task(project_id, title, description, deadline, total_score, status) VALUES
(1, 'Spring Boot入门实验', '搭建第一个Spring Boot项目', '2024-03-15 23:59:59', 100, 3),
(1, 'MyBatis-Plus持久层实验', '实现CRUD接口', '2024-03-25 23:59:59', 100, 2),
(2, 'Vue3组件开发', '开发登录注册页面', '2024-04-10 23:59:59', 100, 2),
(1, 'RESTful API设计', '设计并实现用户管理接口', '2024-04-20 23:59:59', 100, 1);

-- 验证
SELECT * FROM sys_user;
SELECT * FROM sys_role;
SELECT * FROM sys_user_role;
SELECT * FROM training_project;
SELECT * FROM task;
