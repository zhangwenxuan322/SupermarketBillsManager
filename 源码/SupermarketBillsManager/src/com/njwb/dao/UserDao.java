package com.njwb.dao;

import com.njwb.entity.User;

import java.sql.SQLException;
import java.util.List;

/**
 * 数据访问层，进行增删改查操作
 */
public interface UserDao {
    /**
     * 根据用户名查询
     * @param name 用户名
     * @return 用户信息
     * @throws SQLException
     */
    User selectUserByName(String name) throws SQLException;

    /**
     * 根据id查询
     * @param id
     * @return 用户信息
     * @throws SQLException
     */
    User selectUserById(int id) throws SQLException;

    /**
     * 根据id更新
     * @param user
     * @return
     * @throws SQLException
     */
    int updateByIdUser(User user) throws SQLException;

    /**
     * 根据id删除
     * @param id
     * @return
     * @throws SQLException
     */
    int deletUserById(int id) throws SQLException;

    /**
     * 根据用户名的模糊查询
     * @param name
     * @return
     * @throws SQLException
     */
    List<User> vagueSelectUserByName(String name) throws SQLException;

    /**
     * 查询所有用户信息
     * @return 所有用户
     * @throws SQLException
     */
    List<User> selectAllUsers() throws SQLException;

    /**
     * 新增用户
     * @param user 用户信息
     * @throws SQLException
     */
    void insertUser(User user) throws SQLException;
}
