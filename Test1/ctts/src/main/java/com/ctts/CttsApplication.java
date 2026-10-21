package com.ctts;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * CTTS 校园实训任务管理系统启动类
 */
@SpringBootApplication
@MapperScan("com.ctts.mapper")   // 扫描Mapper接口（第7次课编写UserMapper时生效）
public class CttsApplication {

    public static void main(String[] args) {
        SpringApplication.run(CttsApplication.class, args);
    }
}
