package com.ctts;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 【超前内容】本测试类为数据源连接验证的扩展练习，
 * 当前老师主线测试类为 CttsApplicationTests，本文件保留备查。
 *
 * 数据库连接验证测试类
 * 运行 testConnection()，若能获取 Connection 并打印数据库元数据，则说明数据源配置正确
 */
@SpringBootTest
public class DataSourceConnectionTest {

    @Autowired
    private DataSource dataSource;  // Spring Boot 自动注入 HikariDataSource

    @Test
    public void testConnection() throws SQLException {
        System.out.println("========== 数据源类型 ==========");
        System.out.println("DataSource = " + dataSource.getClass().getName());

        try (Connection conn = dataSource.getConnection()) {
            System.out.println("========== 连接成功 ==========");
            System.out.println("Connection = " + conn);
            System.out.println("是否已关闭 = " + conn.isClosed());
            System.out.println("是否有效 = " + conn.isValid(3));

            DatabaseMetaData metaData = conn.getMetaData();
            System.out.println("========== 数据库元数据 ==========");
            System.out.println("数据库产品名 = " + metaData.getDatabaseProductName());
            System.out.println("数据库版本 = " + metaData.getDatabaseProductVersion());
            System.out.println("驱动名 = " + metaData.getDriverName());
            System.out.println("驱动版本 = " + metaData.getDriverVersion());
            System.out.println("URL = " + metaData.getURL());
            System.out.println("用户名 = " + metaData.getUserName());

            assertNotNull(conn, "Connection 不应为 null");
            assertFalse(conn.isClosed(), "连接不应处于关闭状态");
            assertTrue(conn.isValid(3), "连接应在3秒内有效");
        }

        System.out.println("========== 测试通过：数据库连接正常 ==========");
    }
}
