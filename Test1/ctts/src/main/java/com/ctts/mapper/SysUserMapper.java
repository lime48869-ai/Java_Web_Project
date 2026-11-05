package com.ctts.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ctts.entity.SysUser;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 【超前内容】本文件为后续"用户/权限"模块的扩展练习，
 * 当前老师主线（Project/Task）暂未涉及，保留备查。
 *
 * 系统用户 Mapper 接口
 * - 继承 BaseMapper<SysUser> 获得内置 CRUD
 * - 自定义方法用 @Select 注解 SQL 实现
 * 注意：启动类已用 @MapperScan 批量扫描，无需加 @Mapper 注解
 */
public interface SysUserMapper extends BaseMapper<SysUser> {

    /**
     * 按状态查询用户（0禁用 1正常）
     */
    @Select("SELECT id, username, real_name, phone, email, status, create_time, update_time " +
            "FROM sys_user WHERE status = #{status} ORDER BY create_time DESC")
    List<SysUser> selectByStatus(Integer status);

    /**
     * 按用户名模糊查询
     */
    @Select("SELECT * FROM sys_user " +
            "WHERE username LIKE CONCAT('%', #{keyword}, '%')")
    List<SysUser> selectByKeyword(@Param("keyword") String keyword);

    /**
     * 查询邮箱不为空的用户
     */
    @Select("SELECT * FROM sys_user WHERE email IS NOT NULL")
    List<SysUser> selectWithEmail();

    /**
     * 按用户名精确查询（登录时使用）
     */
    @Select("SELECT * FROM sys_user WHERE username = #{username}")
    SysUser selectByUsername(String username);
}
