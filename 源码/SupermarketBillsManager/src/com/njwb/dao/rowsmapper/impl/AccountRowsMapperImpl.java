package com.njwb.dao.rowsmapper.impl;

import com.njwb.dao.rowsmapper.RowsMapper;
import com.njwb.entity.Account;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @program: SupermarketBillsManager
 * @description: 账单结果集实现类
 * @author: 张文轩
 * @create: 2019-10-28 16:28
 **/
public class AccountRowsMapperImpl implements RowsMapper {
    @Override
    public Object objectMapper(ResultSet rs) throws SQLException {
        Account account = new Account();
        account.setA_id(rs.getInt("a_id"));
        account.setA_name(rs.getString("a_name"));
        account.setA_nums(rs.getInt("a_nums"));
        account.setA_unit(rs.getString("a_unit"));
        account.setA_amount(rs.getDouble("a_amount"));
        account.setA_ispayed(rs.getInt("a_ispayed"));
        account.setS_id(rs.getInt("s_id"));
        account.setA_info(rs.getString("a_info"));
        account.setA_date(rs.getDate("a_date"));
        return account;
    }
}
