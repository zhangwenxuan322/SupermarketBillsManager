package com.njwb.dao.impl;

import com.njwb.dao.AccountDao;
import com.njwb.dao.rowsmapper.impl.AccountGroupRowsMapperImpl;
import com.njwb.dao.rowsmapper.impl.AccountRowsMapperImpl;
import com.njwb.dao.rowsmapper.impl.SupplierRowsMapperImpl;
import com.njwb.entity.Account;
import com.njwb.entity.AccountGroup;
import com.njwb.entity.Supplier;
import com.njwb.util.JdbcTelmpate;

import java.sql.SQLException;
import java.util.List;

/**
 * @program: SupermarketBillsManager
 * @description: 账单管理数据访问层实现类
 * @author: 张文轩
 * @create: 2019-10-28 16:27
 **/
public class AccountDaoImpl implements AccountDao {
    /**
     * 根据商品名和付款情况精确查询
     * @param name
     * @param ispayed
     * @return
     * @throws SQLException
     */
    @Override
    public List<Account> selectAccountByNameAndPayment(String name, int ispayed) throws SQLException {
        Account account = null;
        String sql = "select a_id,a_name,a_nums,a_unit,a_amount,a_ispayed,s_id,a_info,a_date from account where a_name=? and a_ispayed=?";
        List<Account> list = JdbcTelmpate.executeQuery(sql, new AccountRowsMapperImpl(), name, ispayed);
        return list;
    }

    /**
     * 模糊查询用户信息
     * @param name 商品名
     * @param ispayed 商品付款情况
     * @return
     * @throws SQLException
     */
    @Override
    public List<Account> vagueSelectAccountByNameAndPayment(String name, int ispayed) throws SQLException {
        String sql = "select a_id,a_name,a_nums,a_unit,a_amount,a_ispayed,s_id,a_info,a_date from account where a_name like \"%\"?\"%\" and a_ispayed=?";
        List<Account> list = JdbcTelmpate.executeQuery(sql, new AccountRowsMapperImpl(), name, ispayed);
        return list;
    }

    /**
     * 根据商品名模糊查询
     * @param name 商品名
     * @return
     * @throws SQLException
     */
    @Override
    public List<Account> vagueSelectAccountByName(String name) throws SQLException {
        String sql = "select a_id,a_name,a_nums,a_unit,a_amount,a_ispayed,s_id,a_info,a_date from account where a_name like \"%\"?\"%\"";
        List<Account> list = JdbcTelmpate.executeQuery(sql, new AccountRowsMapperImpl(), name);
        return list;
    }

    /**
     * 根据付款情况查询
     * @param ispayed
     * @return
     * @throws SQLException
     */
    @Override
    public List<Account> selectAccountByPayment(int ispayed) throws SQLException {
        String sql = "select a_id,a_name,a_nums,a_unit,a_amount,a_ispayed,s_id,a_info,a_date from account where a_ispayed=?";
        List<Account> list = JdbcTelmpate.executeQuery(sql, new AccountRowsMapperImpl(), ispayed);
        return list;
    }

    /**
     * 根据s_id获取商品
     * @param s_id
     * @return
     * @throws SQLException
     */
    @Override
    public List<Account> selectAccountBySid(int s_id) throws SQLException {
        String sql = "select a_id,a_name,a_nums,a_unit,a_amount,a_ispayed,s_id,a_info,a_date from account where s_id=?";
        List<Account> list = JdbcTelmpate.executeQuery(sql, new AccountRowsMapperImpl(), s_id);
        return list;
    }

    /**
     * 查询所有账单信息
     * @return
     * @throws SQLException
     */
    @Override
    public List<Account> selectAllAccounts() throws SQLException {
        String sql = "select a_id,a_name,a_nums,a_unit,a_amount,a_ispayed,s_id,a_info,a_date from account";
        List<Account> list = JdbcTelmpate.executeQuery(sql, new AccountRowsMapperImpl(), null);
        return list;
    }

    /**
     * 根据外键查询供应商信息
     * @param s_id
     * @return
     * @throws SQLException
     */
    @Override
    public Supplier selectSidFromForeignKey(int s_id) throws SQLException {
        Supplier supplier = null;
        String sql = "select s.s_id,s.s_name,s.s_info,s.s_linkman,s.s_phone,s.s_faxes,s.s_address from account a,supplier s where a.s_id=s.s_id and a.s_id=?";
        List<Supplier> list = JdbcTelmpate.executeQuery(sql, new SupplierRowsMapperImpl(), s_id);
        if (list.size() > 0) {
            supplier = list.get(0);
        }
        return supplier;
    }

    /**
     * 根据id查找商品
     * @param id
     * @return
     * @throws SQLException
     */
    @Override
    public Account selectAccountById(int id) throws SQLException {
        Account account = null;
        String sql = "select a_id,a_name,a_nums,a_unit,a_amount,a_ispayed,s_id,a_info,a_date from account where a_id=?";
        List<Account> list = JdbcTelmpate.executeQuery(sql, new AccountRowsMapperImpl(), id);
        if (list.size() > 0) {
            account = list.get(0);
        }
        return account;
    }

    /**
     * 插入账单
     * @param account
     * @throws SQLException
     */
    @Override
    public void insertAccount(Account account) throws SQLException {
        String sql = "insert into account(a_id,a_name,a_nums,a_unit,a_amount,a_ispayed,s_id,a_info,a_date) values(?,?,?,?,?,?,?,?,?)";
        JdbcTelmpate.executeUpdate(sql,
                account.getA_id(), account.getA_name(), account.getA_nums(), account.getA_unit(), account.getA_amount(),
                account.getA_ispayed(), account.getS_id(), account.getA_info(),account.getA_date());
    }

    /**
     * 商品信息修改
     * @param account
     * @return
     * @throws SQLException
     */
    @Override
    public int updateByIdAccount(Account account) throws SQLException {
        String sql = "update account set a_name=?,a_nums=?,a_unit=?,a_amount=?,a_ispayed=?,s_id=?,a_info=?,a_date=? where a_id=?";
        return JdbcTelmpate.executeUpdate(sql, account.getA_name(), account.getA_nums(), account.getA_unit(), account.getA_amount(), account.getA_ispayed()
                , account.getS_id(), account.getA_info(), account.getA_date(), account.getA_id());
    }

    /**
     * 根据id删除商品
     * @param id
     * @return
     * @throws SQLException
     */
    @Override
    public int deletAccountById(int id) throws SQLException {
        String sql = "delete from account where a_id=?";
        int count = JdbcTelmpate.executeUpdate(sql, id);
        return count;
    }

    /**
     * 商品分组查询
     * @return
     * @throws SQLException
     */
    @Override
    public List<AccountGroup> selectByGroup() throws SQLException {
        String sql = "select a_name,sum(a_nums),sum(a_amount),s_name from account,supplier where account.s_id=supplier.s_id group by a_name,s_name";
        List<AccountGroup> list =  JdbcTelmpate.executeQuery(sql, new AccountGroupRowsMapperImpl());
        return list;
    }
}
