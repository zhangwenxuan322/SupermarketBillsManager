package com.njwb.util;

import com.njwb.dao.rowsmapper.RowsMapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @program: SupermarketBillsManager
 * @description: 单独封装增删改查
 * @author: 张文轩
 * @create: 2019-10-25 11:49
 **/
public class JdbcTelmpate {
    /**
     * 增删改
     * @param sql sql语句
     * @param params 参数集
     * @return sql执行影响的行数
     * @throws SQLException
     */
    public static int executeUpdate(String sql, Object... params) throws SQLException {
        // 获取连接
        Connection conn = JdbcUtil.getConn();
        // sql执行器
        PreparedStatement pstmt = conn.prepareStatement(sql);
        // 设置参数
        setParams(pstmt,params);
        //执行sql
        int count = pstmt.executeUpdate();
        //关闭连接
        JdbcUtil.close(pstmt, null);
        return count;
    }

    /**
     *  查询
     * @param sql sql语句
     * @param rows 结果集
     * @param params 参数集
     * @return
     * @throws SQLException
     */
    public static List executeQuery(String sql, RowsMapper rows, Object... params) throws SQLException{
        List list = new ArrayList();
        // 获取连接
        Connection conn = JdbcUtil.getConn();
        // sql执行器
        PreparedStatement pstmt = conn.prepareStatement(sql);
        // 设置参数
        setParams(pstmt,params);
        //执行sql
        ResultSet rs = pstmt.executeQuery();
        while(rs.next()){
            Object obj = rows.objectMapper(rs);
            list.add(obj);

        }
        JdbcUtil.close(pstmt, rs);
        return list;
    }

    /**
     *  设置参数
     * @param pstmt sql执行器
     * @param params 参数集
     */
    private static void setParams(PreparedStatement pstmt, Object[] params) throws SQLException{
        if(params!=null&&params.length>0){
            for (int i = 0; i < params.length; i++) {
                pstmt.setObject(i + 1, params[i]);
            }
        }
    }
}
