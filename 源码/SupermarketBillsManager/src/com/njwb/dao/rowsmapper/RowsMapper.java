package com.njwb.dao.rowsmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface RowsMapper {
    /**
     * 处理结果集的方法
     * @param rs 将来需要处理的结果集对象
     * @return Object--》具体处理的对象是不确定的
     * @throws SQLException
     */
    Object objectMapper(ResultSet rs) throws SQLException;
}
