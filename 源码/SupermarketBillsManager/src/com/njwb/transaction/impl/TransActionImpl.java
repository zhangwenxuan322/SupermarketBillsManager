package com.njwb.transaction.impl;

import com.njwb.transaction.TransAction;
import com.njwb.util.JdbcUtil;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @program: SupermarketBillsManager
 * @description: 事务实现类
 * @author: 张文轩
 * @create: 2019-10-27 23:43
 **/
public class TransActionImpl implements TransAction {
    /**
     * 开始
     */
    @Override
    public void begin() {
        // 获取数据库连接
        Connection conn = JdbcUtil.getConn();
        // 开启事务->false
        try {
            conn.setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 提交
     */
    @Override
    public void commit() {
        // 获取数据库连接
        Connection conn = JdbcUtil.getConn();
        try {
            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // 关闭数据库连接
        JdbcUtil.closeConn();
    }

    /**
     * 回滚
     */
    @Override
    public void rollback() {
        // 获取数据库连接
        Connection conn = JdbcUtil.getConn();
        try {
            conn.rollback();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // 关闭数据库连接
        JdbcUtil.closeConn();
    }
}
