package com.njwb.dao.rowsmapper.impl;

import com.njwb.dao.rowsmapper.RowsMapper;
import com.njwb.entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @program: SupermarketBillsManager
 * @description: 用户结果集接口实现类
 * @author: 张文轩
 * @create: 2019-10-25 14:12
 **/
public class UserRowsMapperImpl implements RowsMapper {
    @Override
    public Object objectMapper(ResultSet rs) throws SQLException {
        User user = new User();
        user.setU_id(rs.getInt("u_id"));
        user.setU_name(rs.getString("u_name"));
        user.setU_password(rs.getString("u_password"));
        user.setU_gender(rs.getString("u_gender"));
        user.setU_age(rs.getInt("u_age"));
        user.setU_phone(rs.getString("u_phone"));
        user.setU_address(rs.getString("u_address"));
        user.setU_auth(rs.getString("u_auth"));
        return user;
    }
}
