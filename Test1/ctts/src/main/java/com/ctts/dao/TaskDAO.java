package com.ctts.dao;

import com.ctts.entity.Task;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


public class TaskDAO {

    private static final String URL =
            "jdbc:mysql://localhost:3306/ctts?useSSL=false&serverTimezone=Asia/Shanghai&characterEncoding=utf8&allowPublicKeyRetrieval=true";
    private static final String USER = "root";
    private static final String PASSWORD = "root123456";
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";

    /**
     * 获取所有任务
     */
    public List<Task> getTasks() {
        String SQL = "SELECT id, project_id, title, description, deadline, total_score, status, create_time, update_time FROM task";

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;

        try {
            // 1. 加载驱动
            Class.forName(DRIVER);

            // 2. 获取连接
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("连接成功：" + connection);

            // 3. 准备SQL
            preparedStatement = connection.prepareStatement(SQL);

            // 4. 执行查询
            rs = preparedStatement.executeQuery();

            // 5. 处理结果集
            List<Task> list = new ArrayList<>();
            while (rs.next()) {
                Task task = new Task();
                task.setId(rs.getLong("id"));
                task.setProjectId(rs.getLong("project_id"));
                task.setTitle(rs.getString("title"));
                task.setDescription(rs.getString("description"));
                task.setDeadline(rs.getObject("deadline", LocalDateTime.class));
                task.setTotalScore(rs.getInt("total_score"));
                task.setStatus(rs.getInt("status"));
                task.setCreateTime(rs.getObject("create_time", LocalDateTime.class));
                task.setUpdateTime(rs.getObject("update_time", LocalDateTime.class));
                list.add(task);
                System.out.println(task);
            }
            return list;

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // 6. 关闭资源
            if (rs != null) try { rs.close(); } catch (SQLException e) { e.printStackTrace(); }
            if (preparedStatement != null) try { preparedStatement.close(); } catch (SQLException e) { e.printStackTrace(); }
            if (connection != null) try { connection.close(); } catch (SQLException e) { e.printStackTrace(); }
        }
        return null;
    }

    // 按ID查询任务
    public Task getTaskById(Long id) {
        String SQL = "SELECT id, project_id, title, description, deadline, total_score, `status`, create_time, update_time FROM task WHERE id = ?";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        try {
            Class.forName(DRIVER);
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setLong(1, id);
            rs = preparedStatement.executeQuery();

            Task task = new Task();
            while (rs.next()) {
                task.setId(rs.getLong("id"));
                task.setProjectId(rs.getLong("project_id"));
                task.setTitle(rs.getString("title"));
                task.setDescription(rs.getString("description"));
                task.setDeadline(rs.getObject("deadline", LocalDateTime.class));
                task.setTotalScore(rs.getInt("total_score"));
                task.setStatus(rs.getInt("status"));
                task.setCreateTime(rs.getObject("create_time", LocalDateTime.class));
                task.setUpdateTime(rs.getObject("update_time", LocalDateTime.class));
            }
            return task;

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) try { rs.close(); } catch (SQLException e) { e.printStackTrace(); }
            if (preparedStatement != null) try { preparedStatement.close(); } catch (SQLException e) { e.printStackTrace(); }
            if (connection != null) try { connection.close(); } catch (SQLException e) { e.printStackTrace(); }
        }
        return null;
    }

    public static void main(String[] args) {
        TaskDAO taskDAO = new TaskDAO();
        Task task = new Task();
        task.setProjectId(2L);
        task.setTitle("Task 1");
        task.setDescription("This is task 1");
        task.setDeadline(LocalDateTime.now().plusDays(30));
        task.setTotalScore(100);
        task.setStatus(2);
        task.setCreateTime(LocalDateTime.now());
        task.setUpdateTime(LocalDateTime.now());
        taskDAO.addTask(task);
    }


    public void addTask(Task task) {
        String SQL = "INSERT INTO task(project_id, title, description, deadline, total_score, status, create_time, update_time) " +
                "VALUES(?, ?, ?, ?, ?, ?, ?, ?)";

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            // 1. 加载驱动
            Class.forName("com.mysql.cj.jdbc.Driver");

            // 2. 获取连接
            connection = DriverManager.getConnection(URL, USER, PASSWORD);

            // 关闭自动提交，开启事务
            connection.setAutoCommit(false);

            // 3. 准备SQL
            preparedStatement = connection.prepareStatement(SQL);

            // 4. 设置参数（按SQL中?的顺序）
            preparedStatement.setLong(1, task.getProjectId());
            preparedStatement.setString(2, task.getTitle());
            preparedStatement.setString(3, task.getDescription());
            preparedStatement.setObject(4, task.getDeadline());
            preparedStatement.setInt(5, task.getTotalScore());
            preparedStatement.setInt(6, task.getStatus());
            preparedStatement.setObject(7, task.getCreateTime());
            preparedStatement.setObject(8, task.getUpdateTime());

            // 5. 执行插入
            int rows = preparedStatement.executeUpdate();
            System.out.println("插入成功，影响行数 = " + rows);

            // 提交事务
            connection.commit();

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            // 出错回滚
            try {
                if (connection != null) {
                    connection.rollback();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } finally {
            // 6. 关闭资源
            if (preparedStatement != null) try { preparedStatement.close(); } catch (SQLException e) { e.printStackTrace(); }
            if (connection != null) try { connection.close(); } catch (SQLException e) { e.printStackTrace(); }
        }
    }

}
