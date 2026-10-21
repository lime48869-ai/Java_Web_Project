-- ============================================================
-- 实验三：MyBatis-Plus 持久层开发 —— training_project 建表脚本
-- 在 Navicat 中执行（ctts 数据库下）
-- ============================================================

USE ctts;

-- 创建 training_project 实训项目表
DROP TABLE IF EXISTS training_project;
CREATE TABLE training_project (
    id          BIGINT       NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    name        VARCHAR(100) NOT NULL COMMENT '项目名称',
    semester    VARCHAR(20)  NOT NULL COMMENT '学期，如 2025春季',
    description VARCHAR(500) DEFAULT NULL COMMENT '项目描述',
    status      INT          DEFAULT 0 COMMENT '状态：0未开始 1进行中 2已结束',
    create_time DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='实训项目表';

-- 插入测试数据
INSERT INTO training_project(name, semester, description, status) VALUES
('电商网站开发实训', '2025春季', '基于SpringBoot+Vue的电商全栈项目', 1),
('校园二手交易平台', '2025春季', '移动端二手物品交易平台开发', 0),
('智能宿舍管理系统', '2025秋季', '宿舍报修、查寝、电费查询一体化系统', 0),
('在线考试系统', '2024秋季', '支持自动组卷、在线答题、自动判分', 2);

-- 验证
SELECT * FROM training_project;
