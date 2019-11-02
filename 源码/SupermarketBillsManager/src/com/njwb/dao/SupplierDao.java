package com.njwb.dao;

import com.njwb.entity.Supplier;
import com.njwb.entity.SupplierGroup;

import java.sql.SQLException;
import java.util.List;

/**
 * @program: SupermarketBillsManager
 * @description: 供应商数据访问层接口
 * @author: 张文轩
 * @create: 2019-10-28 13:16
 **/
public interface SupplierDao {

    /**
     * 根据供应商名称模糊查询
     * @param name 供应商名称
     * @return 查询到的供应商
     * @throws SQLException
     */
    List<Supplier> vagueSelectSupplierByName(String name) throws SQLException;

    /**
     * 查询所有供应商
     * @return 查询到的供应商
     * @throws SQLException
     */
    List<Supplier> selectAllSuppliers() throws SQLException;

    /**
     * 供应商修改
     * @param supplier 修改后的供应商
     * @return
     * @throws SQLException
     */
    int updateByIdSupplier(Supplier supplier) throws SQLException;

    /**
     * 根据名称查询供应商
     * @param name 供应商名称
     * @return
     * @throws SQLException
     */
    Supplier selectSupplierByName(String name) throws SQLException;

    /**
     * 根据id删除供应商
     * @param id
     * @return
     * @throws SQLException
     */
    int deletSupplierById(int id) throws SQLException;

    /**
     * 新增供应商
     * @param supplier 供应商信息
     * @throws SQLException
     */
    void insertSupplier(Supplier supplier) throws SQLException;

    /**
     * 根据id查找供应商
     * @param id
     * @return
     * @throws SQLException
     */
    Supplier selectSupplierById(int id) throws SQLException;

    /**
     * 供应商分组查询
     * @return
     * @throws SQLException
     */
    List<SupplierGroup> selectByGroup() throws SQLException;
}
