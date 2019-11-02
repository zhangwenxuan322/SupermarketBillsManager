package com.njwb.service.impl;

import com.njwb.dao.AccountDao;
import com.njwb.entity.Account;
import com.njwb.entity.AccountGroup;
import com.njwb.entity.Supplier;
import com.njwb.factory.ObjectFactory;
import com.njwb.myexception.MyException;
import com.njwb.service.AccountService;
import com.njwb.transaction.TransAction;

import java.sql.SQLException;
import java.util.List;

/**
 * @program: SupermarketBillsManager
 * @description: 账单业务逻辑层实现类
 * @author: 张文轩
 * @create: 2019-10-28 16:34
 **/
public class AccountServiceImpl implements AccountService {
    //通过对象工厂获取
    private AccountDao adao = (AccountDao) ObjectFactory.objectMap.get("AccountDao");
    private TransAction ta = (TransAction)ObjectFactory.objectMap.get("TransAction");
    /**
     * 根据商品名和付款情况查询
     * @param name
     * @param ispayed
     * @return
     * @throws MyException
     */
    @Override
    public String[][] accountSelector(String name, int ispayed) throws MyException {
        String[][] results = null;
        if ((name == null || name.equals("")) && (ispayed == -1)) {// 如果全部传参为空，则全部查询
            try {
                List<Account> list = adao.selectAllAccounts();
                // 将接收到的list转化成二维数组
                results = listCovert(list);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }else if ((name == null || name.equals("")) && (ispayed != -1)) {// 如果商品名为空，付款情况有值，则根据付款情况查询
            try {
                List<Account> list = adao.selectAccountByPayment(ispayed);
                results = listCovert(list);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }else if ((name != null && !name.equals("")) && (ispayed == -1)) {// 如果商品名有值，付款情况为空，则根据商品名模糊查询
            try {
                List<Account> list = adao.vagueSelectAccountByName(name);
                results = listCovert(list);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }else {// 正常传参按姓名模糊查询，付款情况查询
            try {
                List<Account> list = adao.vagueSelectAccountByNameAndPayment(name, ispayed);
                if (list.size() == 0) {
                    String[][] supplierArr = new String[0][0];
                    results = supplierArr;
                } else {
                    // 收到的user放入二维数组
                    results = listCovert(list);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return results;
    }

    /**
     * 根据id查找商品
     * @param id
     * @return
     * @throws MyException
     */
    @Override
    public Account queryByIdAccount(int id) throws MyException {
        Account account = null;
        try {
            account = adao.selectAccountById(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return account;
    }

    /**
     * 添加商品
     * @param account
     * @throws MyException
     */
    @Override
    public void addAccount(Account account) throws MyException {
        if (account.getA_name() == null || account.getA_name().equals("")) throw new MyException("商品名未填写！");
        else if (account.getA_nums() == 0) throw new MyException("交易数量未填写！");
        else if (account.getA_unit() == null || account.getA_unit().equals("")) throw new MyException("交易单位未填写！");
        else if (account.getA_amount() == 0) throw new MyException("交易金额未填写！");
        else if (account.getA_date() == null || account.getA_date().equals("")) throw new MyException("交易时间未填写！");
        try {
            // 验证供应商是否存在
            List<Account> list = adao.selectAccountByNameAndPayment(account.getA_name(), account.getA_ispayed());
            if (list != null) {
                for (Account la : list) {
                    if (la.getA_name().equals(account.getA_name()) && la.getA_ispayed() == account.getA_ispayed())
                        throw new MyException("该状态的商品已存在！");
                }
            }
            // 事务开启
            ta.begin();
            // 插入数据
            adao.insertAccount(account);
            // 事务关闭
            ta.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            // 事务回滚
            ta.rollback();
        }
    }

    /**
     * 根据id修改商品信息
     * @param account
     * @param originName
     * @throws MyException
     */
    @Override
    public void updateByIdAccount(Account account, String originName, int originPay) throws MyException {
        if (account.getA_name() == null || account.getA_name().equals("")) throw new MyException("商品名未填写！");
        else if (account.getA_nums() == 0) throw new MyException("交易数量未填写！");
        else if (account.getA_unit() == null || account.getA_unit().equals("")) throw new MyException("交易单位未填写！");
        else if (account.getA_amount() == 0) throw new MyException("交易金额未填写！");
        else if (account.getA_date() == null || account.getA_date().equals("")) throw new MyException("交易时间未填写！");
        try {
            // 验证供应商是否存在
            List<Account> list = adao.selectAccountByNameAndPayment(account.getA_name(), account.getA_ispayed());
            if (list != null) {
                if (!account.getA_name().equals(originName) || account.getA_ispayed() != originPay) {
                    for (Account la : list) {
                        if (la.getA_name().equals(account.getA_name()) && la.getA_ispayed() == account.getA_ispayed())
                            throw new MyException("该状态的商品已存在！");
                    }
                }
            }
            // 事务开启
            ta.begin();
            // 插入数据
            adao.updateByIdAccount(account);
            // 事务关闭
            ta.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            // 事务回滚
            ta.rollback();
        }
    }

    /**
     * 根据id集删除商品
     * @param ids
     * @throws MyException
     */
    @Override
    public void deleteByIdAccount(int[] ids) throws MyException {
        try {
            // 事务开启
            ta.begin();
            for (int i = 0;i < ids.length;i++) {
                // 删除操作
                adao.deletAccountById(ids[i]);
            }
            // 事务提交
            ta.commit();
        } catch (SQLException e) {
            // 事务回滚
            ta.rollback();
            throw new MyException("删除失败！");
        }
    }

    /**
     * 商品分组查询
     * @return
     * @throws MyException
     */
    @Override
    public String[][] selectByGroup() throws MyException {
        String[][] results = null;
        try {
            List<AccountGroup> list = adao.selectByGroup();
            String[][] listToStr = new String[list.size()][4];
            for (int i = 0;i < listToStr.length;i++) {
                AccountGroup accountGroup = list.get(i);
                for (int j = 0; j < listToStr[i].length; j++) {
                    switch (j) {
                        case 0:
                            listToStr[i][j] = accountGroup.getName();
                            break;
                        case 1:
                            listToStr[i][j] = String.valueOf(accountGroup.getNums());
                            break;
                        case 2:
                            listToStr[i][j] = String.valueOf(accountGroup.getAmount());
                            break;
                        case 3:
                            listToStr[i][j] = accountGroup.getSupName();
                            break;
                    }
                }
                results = listToStr;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return results;
    }

    /**
     * 集合转换
     * @param list 集合
     */
    public String[][] listCovert(List<Account> list) {
        // 将接收到的list转化成二维数组
        String[][] listToStr = new String[list.size()][8];
        for (int i = 0;i < listToStr.length;i++) {
            Account account = list.get(i);
            for (int j = 0; j < listToStr[i].length; j++) {
                switch (j) {
                    case 0:
                        listToStr[i][j] = String.valueOf(account.getA_id());
                        break;
                    case 1:
                        listToStr[i][j] = account.getA_name();
                        break;
                    case 2:
                        listToStr[i][j] = String.valueOf(account.getA_nums());
                        break;
                    case 3:
                        listToStr[i][j] = String.valueOf(account.getA_amount());
                        break;
                    case 4:
                        if (account.getA_ispayed() == 0) listToStr[i][j] = "是";
                        else if (account.getA_ispayed() == 1) listToStr[i][j] = "否";
                        break;
                    case 5:
                        try {
                            Supplier supplier = adao.selectSidFromForeignKey(account.getS_id());
                            if (supplier != null) {
                                listToStr[i][j] = supplier.getS_name();
                            }else listToStr[i][j] = "";
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        break;
                    case 6:
                        listToStr[i][j] = account.getA_info();
                        break;
                    case 7:
                        listToStr[i][j] = String.valueOf(account.getA_date());
                        break;
                }
            }
        }
        return listToStr;
    }
}
