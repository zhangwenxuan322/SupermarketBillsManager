package com.njwb.util;

import org.apache.commons.dbcp.BasicDataSourceFactory;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @program: SupermarketBillsManager
 * @description: 管理数据库连接
 * @author: 张文轩
 * @create: 2019-10-25 11:51
 **/
public class JdbcUtil {
    //创建全集本地连接
    private static DataSource ds = null;
    //创建线程本地变量
    private static ThreadLocal<Connection> threadLocal = new ThreadLocal<Connection>();
    //静态代码块加载配置文件
    static {
        Properties prop = new Properties();
        try {
            prop.load(new FileInputStream("src/datasource.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            ds = BasicDataSourceFactory.createDataSource(prop);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取本地连接
     * @return Connection
     */
    public static Connection getConn() {
        Connection conn = threadLocal.get();
        if (conn == null) {
            // 从本地数据源中获取
            try {
                conn = ds.getConnection();
                // 对数据源进行管理
                threadLocal.set(conn);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return conn;
    }
    /**
     * 释放连接
     */
    public static void closeConn() {
        // 从连接池中获取连接
        Connection conn = threadLocal.get();
        if (conn != null) {
            // 将连接进行释放
            threadLocal.remove();
        }
        try {
            // 这里的关闭不是关闭数据库连接，不是真正将连接关闭。仅仅是释放手里的连接到连接池中
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 关闭sql执行器和结果集
     * @param pstmt sql执行器
     * @param rs 结果集
     */
    public static void close(PreparedStatement pstmt, ResultSet rs) {
        if (pstmt != null) {
            try {
                pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
