package com.njwb.dao.rowsmapper.impl;

import com.njwb.dao.rowsmapper.RowsMapper;
import com.njwb.entity.SupplierGroup;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @program: SupermarketBillsManager
 * @description: 供应商分组查询结果集
 * @author: 张文轩
 * @create: 2019-10-29 16:01
 **/
public class SupplierGroupRowsMapperImpl implements RowsMapper {
    @Override
    public Object objectMapper(ResultSet rs) throws SQLException {
        SupplierGroup sp = new SupplierGroup();
        sp.setId(rs.getInt("s_id"));
        sp.setName(rs.getString("s_name"));
        sp.setAmount(rs.getInt("sum(account.a_amount)"));
        sp.setCount(rs.getInt("count(a_name)"));
        sp.setCount(rs.getInt("sum(a_nums)"));
        return sp;
    }
}
