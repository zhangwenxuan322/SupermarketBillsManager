package com.njwb.dao;

import com.njwb.entity.Account;
import com.njwb.entity.AccountGroup;
import com.njwb.entity.Supplier;

import java.sql.SQLException;
import java.util.List;

/**
 * @program: SupermarketBillsManager
 * @description: 账单管理数据访问层接口
 * @author: 张文轩
 * @create: 2019-10-28 16:27
 **/
public interface AccountDao {

    /**
     * 根据商品名和付款情况精确查询
     * @param name
     * @param ispayed
     * @return
     * @throws SQLException
     */
    List<Account> selectAccountByNameAndPayment(String name, int ispayed) throws SQLException;

    /**
     * 根据商品名，付款情况模糊查询
     * @param name 商品名
     * @param ispayed 商品付款情况
     * @return
     * @throws SQLException
     */
    List<Account> vagueSelectAccountByNameAndPayment(String name, int ispayed) throws SQLException;

    /**
     * 根据商品名模糊查询
     * @param name 商品名
     * @return
     * @throws SQLException
     */
    List<Account> vagueSelectAccountByName(String name) throws SQLException;

    /**
     * 根据付款情况查询
     * @param ispayed
     * @return
     * @throws SQLException
     */
    List<Account> selectAccountByPayment(int ispayed) throws SQLException;

    /**
     * 根据s_id获取商品
     * @param s_id
     * @return
     * @throws SQLException
     */
    List<Account> selectAccountBySid(int s_id) throws SQLException;

    /**
     * 查询所有账单信息
     * @return
     * @throws SQLException
     */
    List<Account> selectAllAccounts() throws SQLException;

    /**
     * 根据外键查询供应商信息
     * @param s_id
     * @return
     * @throws SQLException
     */
    Supplier selectSidFromForeignKey(int s_id) throws SQLException;

    /**
     * 根据id查找商品
     * @param id
     * @return
     * @throws SQLException
     */
    Account selectAccountById(int id) throws SQLException;

    /**
     * 插入账单
     * @param account
     * @throws SQLException
     */
    void insertAccount(Account account) throws SQLException;

    /**
     * 商品信息修改
     * @param account
     * @return
     * @throws SQLException
     */
    int updateByIdAccount(Account account) throws SQLException;

    /**
     * 根据id删除商品
     * @param id
     * @return
     * @throws SQLException
     */
    int deletAccountById(int id) throws SQLException;

    /**
     * 商品分组查询
     * @return
     * @throws SQLException
     */
    List<AccountGroup> selectByGroup() throws SQLException;
}
