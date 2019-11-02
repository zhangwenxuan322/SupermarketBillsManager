package com.njwb.dao.impl;

import com.njwb.dao.SupplierDao;
import com.njwb.dao.rowsmapper.impl.SupplierGroupRowsMapperImpl;
import com.njwb.dao.rowsmapper.impl.SupplierRowsMapperImpl;
import com.njwb.dao.rowsmapper.impl.UserRowsMapperImpl;
import com.njwb.entity.Supplier;
import com.njwb.entity.SupplierGroup;
import com.njwb.util.JdbcTelmpate;

import java.sql.SQLException;
import java.util.List;

/**
 * @program: SupermarketBillsManager
 * @description: 供应商数据访问层实现类
 * @author: 张文轩
 * @create: 2019-10-28 13:17
 **/
public class SupplierDaoImpl implements SupplierDao {
    /**
     * 根据供应商名称模糊查询
     * @param name 供应商名称
     * @return 查询到的供应商
     * @throws SQLException
     */
    @Override
    public List<Supplier> vagueSelectSupplierByName(String name) throws SQLException {
        String sql = "select s_id,s_name,s_info,s_linkman,s_phone,s_faxes,s_address from supplier where s_name like \"%\"?\"%\"";
        List<Supplier> list = JdbcTelmpate.executeQuery(sql, new SupplierRowsMapperImpl(), name);
        return list;
    }

    /**
     * 查询所有供应商
     * @return 查询到的供应商
     * @throws SQLException
     */
    @Override
    public List<Supplier> selectAllSuppliers() throws SQLException {
        String sql = "select s_id,s_name,s_info,s_linkman,s_phone,s_faxes,s_address from supplier";
        List<Supplier> list = JdbcTelmpate.executeQuery(sql, new SupplierRowsMapperImpl(), null);
        return list;
    }

    /**
     * 供应商修改
     * @param supplier 修改后的供应商
     * @return
     * @throws SQLException
     */
    @Override
    public int updateByIdSupplier(Supplier supplier) throws SQLException {
        String sql = "update supplier set s_name=?,s_info=?,s_linkman=?,s_phone=?,s_faxes=?,s_address=? where s_id=?";
        return JdbcTelmpate.executeUpdate(sql, supplier.getS_name(), supplier.getS_info(), supplier.getS_linkman(), supplier.getS_phone(), supplier.getS_faxes()
                , supplier.getS_address(), supplier.getS_id());
    }

    /**
     * 根据名称查询供应商
     * @param name 供应商名称
     * @return
     * @throws SQLException
     */
    @Override
    public Supplier selectSupplierByName(String name) throws SQLException {
        Supplier supplier = null;
        String sql = "select s_id,s_name,s_info,s_linkman,s_phone,s_faxes,s_address from supplier where s_name=?";
        List<Supplier> list = JdbcTelmpate.executeQuery(sql, new SupplierRowsMapperImpl(), name);
        if (list.size() > 0) {
            supplier = list.get(0);
        }
        return supplier;
    }

    /**
     * 根据id删除供应商
     * @param id
     * @return
     * @throws SQLException
     */
    @Override
    public int deletSupplierById(int id) throws SQLException {
        String sql = "delete from supplier where s_id=?";
        int count = JdbcTelmpate.executeUpdate(sql, id);
        return count;
    }

    /**
     * 新增供应商
     * @param supplier 供应商信息
     * @throws SQLException
     */
    @Override
    public void insertSupplier(Supplier supplier) throws SQLException {
        String sql = "insert into supplier(s_id,s_name,s_info,s_linkman,s_phone,s_faxes,s_address) values(?,?,?,?,?,?,?)";
        JdbcTelmpate.executeUpdate(sql,
                supplier.getS_id(), supplier.getS_name(), supplier.getS_info(), supplier.getS_linkman(),
                supplier.getS_phone(), supplier.getS_faxes(), supplier.getS_address());
    }

    /**
     * 根据id查找供应商
     * @param id
     * @return
     * @throws SQLException
     */
    @Override
    public Supplier selectSupplierById(int id) throws SQLException {
        Supplier supplier = null;
        String sql = "select s_id,s_name,s_info,s_linkman,s_phone,s_faxes,s_address from supplier where s_id=?";
        List<Supplier> list = JdbcTelmpate.executeQuery(sql, new SupplierRowsMapperImpl(), id);
        if (list.size() > 0) {
            supplier = list.get(0);
        }
        return supplier;
    }

    /**
     * 供应商分组查询
     * @return
     * @throws SQLException
     */
    @Override
    public List<SupplierGroup> selectByGroup() throws SQLException {
        String sql = "select supplier.s_id,supplier.s_name,sum(account.a_amount),count(a_name),sum(a_nums) from account,supplier where account.s_id=supplier.s_id group by supplier.s_id";
        List<SupplierGroup> list =  JdbcTelmpate.executeQuery(sql, new SupplierGroupRowsMapperImpl());
        return list;
    }
}
