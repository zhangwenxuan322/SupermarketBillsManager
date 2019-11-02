package com.njwb.dao.impl;

import com.njwb.dao.UserDao;
import com.njwb.dao.rowsmapper.impl.UserRowsMapperImpl;
import com.njwb.entity.User;
import com.njwb.util.JdbcTelmpate;

import java.sql.SQLException;
import java.util.List;

/**
 * @program: SupermarketBillsManager
 * @description: 用户表数据访问层实现类
 * @author: 张文轩
 * @create: 2019-10-25 14:10
 **/
public class UserDaoImpl implements UserDao {

    /**
     * 根据用户名查询
     * @param name 用户名
     * @return 用户信息
     * @throws SQLException
     */
    @Override
    public User selectUserByName(String name) throws SQLException {
        User user = null;

        String sql = "select u_id,u_name,u_password,u_gender,u_age,u_phone,u_address,u_auth from user where u_name=?";
        List<User> list = JdbcTelmpate.executeQuery(sql, new UserRowsMapperImpl(), name);
        if (list.size() > 0) {
            user = list.get(0);
//            System.out.println(user.toString());
        }
        return user;
    }

    /**
     * 根据id查询
     * @param id
     * @return 用户信息
     * @throws SQLException
     */
    @Override
    public User selectUserById(int id) throws SQLException {
        User user = null;
        String sql = "select u_id,u_name,u_password,u_gender,u_age,u_phone,u_address,u_auth from user where u_id=?";
        List<User> list = JdbcTelmpate.executeQuery(sql, new UserRowsMapperImpl(), id);
        if (list.size() > 0) {
            user = list.get(0);
        }
        return user;
    }

    /**
     * 根据id更新
     * @param user
     * @return
     * @throws SQLException
     */
    @Override
    public int updateByIdUser(User user) throws SQLException {
        String sql = "update user set u_name=?,u_password=?,u_gender=?,u_age=?,u_phone=?,u_address=?,u_auth=? where u_id=?";
        return JdbcTelmpate.executeUpdate(sql, user.getU_name(), user.getU_password(), user.getU_gender(), user.getU_age(), user.getU_phone()
                                        , user.getU_address(), user.getU_auth(), user.getU_id());
    }

    /**
     * 根据id删除
     * @param id
     * @return
     * @throws SQLException
     */
    @Override
    public int deletUserById(int id) throws SQLException {
        String sql = "delete from user where u_id=?";
        int count = JdbcTelmpate.executeUpdate(sql, id);
        return count;
    }

    /**
     * 根据用户名的模糊查询
     * @param name
     * @return
     * @throws SQLException
     */
    @Override
    public List<User> vagueSelectUserByName(String name) throws SQLException {
        String sql = "select u_id,u_name,u_password,u_gender,u_age,u_phone,u_address,u_auth from user where u_name like \"%\"?\"%\"";
        List<User> list = JdbcTelmpate.executeQuery(sql, new UserRowsMapperImpl(), name);
        return list;
    }

    /**
     * 查询所有用户信息
     * @return 所有用户
     * @throws SQLException
     */
    @Override
    public List<User> selectAllUsers() throws SQLException {
        String sql = "select u_id,u_name,u_password,u_gender,u_age,u_phone,u_address,u_auth from user";
        List<User> list = JdbcTelmpate.executeQuery(sql, new UserRowsMapperImpl(), null);
        return list;
    }

    /**
     * 新增用户
     * @param user 用户信息
     * @throws SQLException
     */
    @Override
    public void insertUser(User user) throws SQLException {
        String sql = "insert into user(u_id,u_name,u_password,u_gender,u_age,u_phone,u_address,u_auth) values(?,?,?,?,?,?,?,?)";
        JdbcTelmpate.executeUpdate(sql,
                user.getU_id(), user.getU_name(), user.getU_password(),
                user.getU_gender(), user.getU_age(), user.getU_phone(),
                user.getU_address(), user.getU_auth());
    }
}
