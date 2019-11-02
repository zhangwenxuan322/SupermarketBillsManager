package com.njwb.dao.rowsmapper.impl;

import com.njwb.dao.rowsmapper.RowsMapper;
import com.njwb.entity.AccountGroup;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @program: SupermarketBillsManager
 * @description: 商品分组查询表格模型
 * @author: 张文轩
 * @create: 2019-10-29 16:46
 **/
public class AccountGroupRowsMapperImpl implements RowsMapper {
    @Override
    public Object objectMapper(ResultSet rs) throws SQLException {
        AccountGroup accountGroup = new AccountGroup();
        accountGroup.setName(rs.getString("a_name"));
        accountGroup.setNums(rs.getInt("sum(a_nums)"));
        accountGroup.setAmount(rs.getInt("sum(a_amount)"));
        accountGroup.setSupName(rs.getString("s_name"));
        return accountGroup;
    }
}
