package com.njwb.transaction;

/**
 * @program: SupermarketBillsManager
 * @description: 事务接口
 * @author: 张文轩
 * @create: 2019-10-27 23:43
 **/
public interface TransAction {
    /**
     * 开启
     */
    void begin();

    /**
     * 提交
     */
    void commit();

    /**
     * 关闭
     */
    void rollback();
}
