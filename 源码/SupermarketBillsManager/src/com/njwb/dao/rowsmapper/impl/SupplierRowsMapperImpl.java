package com.njwb.dao.rowsmapper.impl;

import com.njwb.dao.rowsmapper.RowsMapper;
import com.njwb.entity.Supplier;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @program: SupermarketBillsManager
 * @description: 供应商结果集实现类
 * @author: 张文轩
 * @create: 2019-10-28 13:18
 **/
public class SupplierRowsMapperImpl implements RowsMapper {
    @Override
    public Object objectMapper(ResultSet rs) throws SQLException {
        Supplier supplier = new Supplier();
        supplier.setS_id(rs.getInt("s_id"));
        supplier.setS_name(rs.getString("s_name"));
        supplier.setS_info(rs.getString("s_info"));
        supplier.setS_linkman(rs.getString("s_linkman"));
        supplier.setS_phone(rs.getString("s_phone"));
        supplier.setS_faxes(rs.getString("s_faxes"));
        supplier.setS_address(rs.getString("s_address"));
        return supplier;
    }
}
