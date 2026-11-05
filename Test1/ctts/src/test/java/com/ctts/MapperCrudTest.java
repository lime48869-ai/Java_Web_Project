package com.ctts;

import com.ctts.entity.SysUser;
import com.ctts.entity.TrainingProject;
import com.ctts.mapper.SysUserMapper;
import com.ctts.mapper.TrainingProjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 【超前内容】本测试类为 sys_user / training_project 持久层 CRUD 的扩展练习，
 * 当前老师主线（Project/Task）暂未涉及，保留备查。
 *
 * CTTS 持久层 CRUD 验证测试类
 * 验证 sys_user 与 training_project 两张表的 Mapper 接口与 SQL 映射
 */
@SpringBootTest
public class MapperCrudTest {

    @Autowired
    private SysUserMapper userMapper;

    @Autowired
    private TrainingProjectMapper projectMapper;

    // ===== sys_user BaseMapper 内置方法 =====

    @Test
    public void testUserSelectById() {
        SysUser user = userMapper.selectById(1L);
        System.out.println("selectById(1) = " + user);
        assertNotNull(user);
        assertEquals("admin", user.getUsername());
    }

    @Test
    public void testUserSelectList() {
        List<SysUser> list = userMapper.selectList(null);
        System.out.println("用户总数 = " + list.size());
        list.forEach(System.out::println);
        assertTrue(list.size() >= 5);
    }

    @Test
    public void testUserInsert() {
        SysUser user = new SysUser();
        user.setUsername("test_insert_" + System.currentTimeMillis());
        user.setPassword("123456");
        user.setRealName("插入测试用户");
        user.setPhone("13900000099");
        user.setEmail("test@ctts.com");
        user.setStatus(1);
        int rows = userMapper.insert(user);
        assertEquals(1, rows);
        assertNotNull(user.getId());
        System.out.println("新用户ID = " + user.getId());
        userMapper.deleteById(user.getId());  // 清理
    }

    @Test
    public void testUserUpdateById() {
        SysUser user = userMapper.selectById(1L);
        String oldPhone = user.getPhone();
        user.setPhone("13800000088");
        int rows = userMapper.updateById(user);
        assertEquals(1, rows);
        SysUser updated = userMapper.selectById(1L);
        assertEquals("13800000088", updated.getPhone());
        // 恢复
        user.setPhone(oldPhone);
        userMapper.updateById(user);
    }

    // ===== sys_user 注解 SQL 自定义查询 =====

    @Test
    public void testSelectByStatus() {
        List<SysUser> users = userMapper.selectByStatus(1);
        System.out.println("正常用户数 = " + users.size());
        users.forEach(u -> assertEquals(1, u.getStatus()));
    }

    @Test
    public void testSelectByKeyword() {
        List<SysUser> list = userMapper.selectByKeyword("student");
        System.out.println("包含 student 关键词的用户数 = " + list.size());
        assertTrue(list.size() >= 2);
    }

    @Test
    public void testSelectByUsername() {
        SysUser admin = userMapper.selectByUsername("admin");
        assertNotNull(admin);
        assertEquals("系统管理员", admin.getRealName());
    }

    // ===== training_project BaseMapper 内置方法 =====

    @Test
    public void testProjectSelectById() {
        TrainingProject project = projectMapper.selectById(1L);
        System.out.println("项目1 = " + project);
        assertNotNull(project);
        assertEquals("2024春季Java实训", project.getTitle());
    }

    @Test
    public void testProjectSelectList() {
        List<TrainingProject> list = projectMapper.selectList(null);
        System.out.println("项目总数 = " + list.size());
        assertTrue(list.size() >= 4);
    }

    @Test
    public void testProjectInsert() {
        TrainingProject project = new TrainingProject();
        project.setTitle("测试项目_" + System.currentTimeMillis());
        project.setDescription("测试用");
        project.setTeacherId(2L);
        project.setStartDate(LocalDate.of(2024, 8, 1));
        project.setEndDate(LocalDate.of(2024, 8, 31));
        project.setStatus(1);
        int rows = projectMapper.insert(project);
        assertEquals(1, rows);
        assertNotNull(project.getId());
        projectMapper.deleteById(project.getId());  // 清理
    }

    // ===== training_project XML 自定义查询 =====

    @Test
    public void testProjectSelectByStatus() {
        List<TrainingProject> doing = projectMapper.selectByStatus(2);  // status=2 进行中
        System.out.println("进行中的项目数 = " + doing.size());
        doing.forEach(p -> assertEquals(2, p.getStatus()));
    }

    @Test
    public void testSelectByTeacherId() {
        List<TrainingProject> list = projectMapper.selectByTeacherId(2L);
        System.out.println("教师2负责的项目数 = " + list.size());
        list.forEach(p -> assertEquals(2L, p.getTeacherId()));
    }

    @Test
    public void testInsertByXml() {
        TrainingProject project = new TrainingProject();
        project.setTitle("XML插入测试_" + System.currentTimeMillis());
        project.setDescription("通过XML插入");
        project.setTeacherId(3L);
        project.setStartDate(LocalDate.of(2024, 9, 1));
        project.setEndDate(LocalDate.of(2024, 12, 31));
        project.setStatus(1);
        int rows = projectMapper.insertByXml(project);
        assertEquals(1, rows);
        assertNotNull(project.getId());
        projectMapper.deleteById(project.getId());  // 清理
    }

    @Test
    public void testCountByStatus() {
        long count = projectMapper.countByStatus(2);  // 进行中=2
        System.out.println("进行中项目数 = " + count);
        assertTrue(count >= 2);
    }
}
