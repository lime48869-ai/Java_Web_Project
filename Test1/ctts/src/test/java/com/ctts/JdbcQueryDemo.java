package com.ctts;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * JDBC 完整示例：查询 sys_user 表所有用户
 * 演示 JDBC 核心六步流程
 */
public class JdbcQueryDemo {

    // 数据库连接信息
    private static final String URL =
            "jdbc:mysql://localhost:3306/ctts?useSSL=false&serverTimezone=Asia/Shanghai&characterEncoding=utf8&allowPublicKeyRetrieval=true";
    private static final String USER = "root";
    private static final String PASSWORD = "root123456";
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";

    public static void main(String[] args) {
        // ========== 步骤1：加载驱动 ==========
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            System.err.println("MySQL 驱动加载失败：" + e.getMessage());
            return;
        }

        // ========== 步骤2~6：try-with-resources 自动关闭资源 ==========
        // 资源声明顺序：Connection → PreparedStatement → ResultSet
        String sql = "SELECT id, username, password, name, role, phone, create_time FROM sys_user";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);   // 步骤2：获取连接
             PreparedStatement pstmt = conn.prepareStatement(sql);                  // 步骤3：创建PreparedStatement
             ResultSet rs = pstmt.executeQuery()) {                                // 步骤4：执行SQL

            // ========== 步骤5：处理结果集 ==========
            System.out.println("ID\t用户名\t姓名\t角色\t电话");
            System.out.println("─────────────────────────────────────");
            while (rs.next()) {
                Long id = rs.getLong("id");
                String username = rs.getString("username");
                String name = rs.getString("name");
                String role = rs.getString("role");
                String phone = rs.getString("phone");
                System.out.printf("%d\t%s\t%s\t%s\t%s%n", id, username, name, role, phone);
            }
        } catch (SQLException e) {
            System.err.println("数据库操作异常：" + e.getMessage());
            e.printStackTrace();
        }
        // ========== 步骤6：try-with-resources 自动关闭 rs、pstmt、conn，无需手动close ==========
    }
}
