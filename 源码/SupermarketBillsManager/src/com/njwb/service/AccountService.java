package com.njwb.service;

import com.njwb.entity.Account;
import com.njwb.myexception.MyException;

/**
 * @program: SupermarketBillsManager
 * @description: 账单业务逻辑层接口
 * @author: 张文轩
 * @create: 2019-10-28 16:33
 **/
public interface AccountService {
    /**
     * 根据商品名和付款情况查询
     * @param name
     * @param ispayed
     * @return
     * @throws MyException
     */
    String[][] accountSelector(String name, int ispayed) throws MyException;

    /**
     * 根据id查找商品
     * @param id
     * @return
     * @throws MyException
     */
    Account queryByIdAccount(int id) throws MyException;

    /**
     * 添加商品
     * @param account
     * @throws MyException
     */
    void addAccount(Account account) throws MyException;

    /**
     * 根据id修改商品信息
     * @param account
     * @param originName
     * @throws MyException
     */
    void updateByIdAccount(Account account, String originName ,int originPay) throws MyException;

    /**
     * 根据id集删除商品
     * @param ids
     * @throws MyException
     */
    void deleteByIdAccount(int[] ids) throws MyException;

    /**
     * 商品分组查询
     * @return
     * @throws MyException
     */
    String[][] selectByGroup() throws MyException;
}
