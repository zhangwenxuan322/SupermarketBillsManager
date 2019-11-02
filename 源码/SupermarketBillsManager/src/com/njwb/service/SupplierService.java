package com.njwb.service;

import com.njwb.entity.Supplier;
import com.njwb.entity.SupplierGroup;
import com.njwb.myexception.MyException;

import java.util.List;

/**
 * @program: SupermarketBillsManager
 * @description: 供应商业务逻辑层接口
 * @author: 张文轩
 * @create: 2019-10-28 13:53
 **/
public interface SupplierService {

    /**
     * 供应商名称查询
     * @param name
     * @return
     * @throws MyException
     */
    String[][] supplierSelector(String name) throws MyException;

    /**
     *  添加供应商
     * @param supplier
     * @throws MyException
     */
    void addSupplier(Supplier supplier) throws MyException;

    /**
     * 根据id修改供应商信息
     * @param supplier
     * @throws MyException
     */
    void updateByIdSupplier(Supplier supplier, String originName) throws MyException;

    /**
     * 根据id查找供应商
     * @param id
     * @return
     * @throws MyException
     */
    Supplier queryByIdSupplier(int id) throws MyException;

    /**
     * 根据姓名查找id
     * @param name
     * @return
     * @throws MyException
     */
    int queryByNameSupplier(String name) throws MyException;

    /**
     * 根据id删除供应商
     * @param ids
     * @throws MyException
     */
    void deleteByIdSupplieer(int[] ids) throws MyException;

    /**
     * 导入操作
     * @param supplier
     */
    void importOperation(Supplier supplier);

    /**
     * 根据供应商分组查询
     * @return
     * @throws MyException
     */
    String[][] selectByGroup() throws MyException;
}
